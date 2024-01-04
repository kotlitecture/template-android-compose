package core.ui.misc.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

class SpannableTextBuilder {

    private val sections: MutableList<SpanSection> = mutableListOf()

    private var offset: Int = 0

    fun append(text: String, span: SpanStyle, onClick: (() -> Unit)? = null): SpannableTextBuilder {
        return append(text, listOf(SpanStyleWrapper(span)), onClick)
    }

    fun append(text: String, onClick: (() -> Unit)? = null): SpannableTextBuilder {
        return append(text, emptyList(), onClick)
    }

    fun append(text: String, color: Color, onClick: (() -> Unit)? = null): SpannableTextBuilder {
        return append(text, SpanStyle(color = color), onClick)
    }

    fun appendBold(text: String, onClick: (() -> Unit)? = null): SpannableTextBuilder {
        return append(text, SpanStyle(fontWeight = FontWeight.Bold), onClick)
    }

    fun appendUnderline(text: String, onClick: (() -> Unit)? = null): SpannableTextBuilder {
        return append(text, SpanStyle(textDecoration = TextDecoration.Underline), onClick)
    }

    fun appendSpace(): SpannableTextBuilder {
        return append(" ")
    }

    fun appendOpenBracket(): SpannableTextBuilder {
        return append("(")
    }

    fun appendCloseBracket(): SpannableTextBuilder {
        return append(")")
    }

    fun and(offset: Int, span: SpanStyle): SpannableTextBuilder {
        val last = sections.last()
        sections[sections.size - 1] =
            last.copy(spans = last.spans.plus(SpanStyleWrapper(span, offset)))
        return this
    }

    private fun append(
        text: String,
        spans: List<SpanStyleWrapper> = emptyList(),
        onClick: (() -> Unit)? = null
    ): SpannableTextBuilder {
        sections.add(SpanSection(text, offset, spans, onClick))
        offset += text.length
        return this
    }

    @Composable
    fun Build(callback: @Composable (annotatedString: AnnotatedString, onClick: (Int) -> Unit) -> Unit) {
        val annotatedString = buildAnnotatedString {
            sections.onEach { section ->
                section.apply(this)
            }
        }
        callback(annotatedString) { offset ->
            val section = sections.find { it.isClicked(offset) }
            section?.onClick?.invoke()
        }
    }

    fun build(): AnnotatedString {
        return buildAnnotatedString {
            sections.onEach { section ->
                section.apply(this)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SpannableTextBuilder

        if (sections != other.sections) return false
        if (offset != other.offset) return false

        return true
    }

    override fun hashCode(): Int {
        var result = sections.hashCode()
        result = 31 * result + offset
        return result
    }

    private data class SpanStyleWrapper(
        val style: SpanStyle,
        val offset: Int = 0
    )

    private data class SpanSection(
        val text: String,
        val startIndex: Int,
        val spans: List<SpanStyleWrapper>,
        val onClick: (() -> Unit)? = null
    ) {

        private val endIndex = startIndex + text.length

        fun isClickable() = onClick != null

        fun isClicked(offset: Int) = isClickable() && offset in startIndex..endIndex

        fun apply(builder: AnnotatedString.Builder) {
            builder.append(text)
            if (onClick != null) {
                builder.pushStringAnnotation(TAG_CLICKABLE, text)
            }
            spans.onEach { span ->
                if (span.offset >= 0) {
                    builder.addStyle(span.style, startIndex + span.offset, endIndex)
                }
            }
        }

        companion object {
            const val TAG_CLICKABLE = "clickable"
        }
    }
}