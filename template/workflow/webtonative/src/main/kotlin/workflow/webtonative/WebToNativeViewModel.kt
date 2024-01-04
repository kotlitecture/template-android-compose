package workflow.webtonative

import android.webkit.JavascriptInterface
import android.webkit.WebView
import core.data.state.StoreObject
import core.essentials.misc.utils.GsonUtils
import core.ui.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import org.tinylog.Logger
import workflow.webtonative.command.WebCommand
import java.util.concurrent.ConcurrentLinkedQueue
import javax.inject.Inject

@HiltViewModel
class WebToNativeViewModel @Inject constructor() : BaseViewModel() {

    val loadedStore = StoreObject(false)
    val webViewStore = StoreObject<WebView>()

    private val commandsFromWeb = ConcurrentLinkedQueue<WebCommand>()
    private val commandsToWeb = ConcurrentLinkedQueue<WebCommand>()

    override fun doBind() {
        launchAsync("doBind") {
            loadedStore.asFlow()
                .filterNotNull()
                .collectLatest { loaded ->
                    if (loaded) {
                        proceedCommands()
                    }
                }
        }
    }

    fun onSetDarkMode(isDark: Boolean) {

    }

    @JavascriptInterface
    fun onSend(commandJson: String) {
        Logger.debug("onSend :: {}", commandJson)
        val command = GsonUtils.toObject(commandJson, WebCommand::class.java) ?: return
        commandsFromWeb.add(command)
        commandsToWeb.add(command)
        proceedCommands()
    }

    private fun proceedCommands() {
        launchAsync("commandsFromWeb") {
            while (true) {
                val command = commandsFromWeb.poll() ?: break
                Logger.debug("commandsFromWeb :: {}", command)
            }
        }
        launchMain("commandsToWeb") {
            while (true) {
                if (!loadedStore.getNotNull()) break
                val webView = webViewStore.get() ?: break
                val command = commandsToWeb.poll() ?: break
                val commandJson = GsonUtils.toString(command)
                Logger.debug("commandsToWeb :: {}", command)
                webView.evaluateJavascript("javascript:${WebToNative.OnReceive}('${commandJson}')") {
                    Logger.debug("commandsToWeb evaluated :: {} -> {}", command.id, it)
                }
            }
        }
    }

}
