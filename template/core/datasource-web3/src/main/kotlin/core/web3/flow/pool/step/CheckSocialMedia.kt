package core.web3.flow.pool.step

import core.essentials.flow.FlowStep
import core.essentials.misc.extensions.takeIfIndex
import core.web3.flow.pool.PoolFlowContext

class CheckSocialMedia : FlowStep<PoolFlowContext>() {

    override suspend fun doProceed(context: PoolFlowContext) {
        val source = context.contractSourceCode ?: return
        context.twitterUrl = SocialMedia.Twitter.extract(source) ?: SocialMedia.X.extract(source)
        context.telegramUrl = SocialMedia.Telegram.extract(source)
        context.discordUrl = SocialMedia.Discord.extract(source)
        val links = findHttpLinks(source)
        if (links.isNotEmpty()) {
            val excludes = setOf(
                context.telegramUrl,
                context.discordUrl,
                context.twitterUrl,
                "zeppelin",
                "ethereum",
            ).filterNotNull()
            context.siteUrl = links.firstOrNull { link -> !excludes.any { link.contains(it) } }
        }
    }

    private fun findHttpLinks(text: String): List<String> {
        val httpLinkRegex = """https?://\S+""".toRegex()
        val matches = httpLinkRegex.findAll(text)
        return matches
            .map { it.value }
            .map {
                val endIndex = it
                    .indexOfAny(charArrayOf(' ', '\n', '\t', '\\', ')', ']', '}'))
                    .takeIfIndex()
                    ?: it.length
                it.substring(0, endIndex).trimIndent()
            }
            .toList()
    }

    enum class SocialMedia(val id: String) {

        Twitter("twitter.com"),

        Discord("discord.gg"),

        Telegram("t.me"),

        X("x.com")

        ;

        fun extract(text: String): String? {
            val startIndex = text.indexOf(id).takeIfIndex() ?: return null
            val endIndex = text
                .indexOfAny(charArrayOf(' ', '\n', '\t', '\\', ')', ']', '}'), startIndex + 1)
                .takeIfIndex()
                ?: text.length
            val id = text.substring(startIndex, endIndex).trimIndent()
            return "https://$id"
        }
    }

}