package app.userflow.webtonative

import android.webkit.JavascriptInterface
import android.webkit.WebView
import app.userflow.webtonative.command.WebCommand
import core.ui.AppViewModel
import core.ui.navigation.NavigationState
import core.ui.state.StoreObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.tinylog.Logger
import java.util.concurrent.ConcurrentLinkedQueue
import javax.inject.Inject

@HiltViewModel
class WebToNativeViewModel @Inject constructor(
    private val navigationState: NavigationState
) : AppViewModel() {

    val loadedStore = StoreObject(false)
    val webViewStore = StoreObject<WebView>()

    private val commandsFromWeb = ConcurrentLinkedQueue<WebCommand>()
    private val commandsToWeb = ConcurrentLinkedQueue<WebCommand>()

    fun onBack() {
        navigationState.onBack()
    }

    override fun doBind() {
        launchAsync("loadedStore") {
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
        val command = Json.decodeFromString<WebCommand>(commandJson)
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
                val commandJson = Json.encodeToString(command)
                Logger.debug("commandsToWeb :: {}", command)
                webView.evaluateJavascript("javascript:${WebToNative.OnReceive}('${commandJson}')") {
                    Logger.debug("commandsToWeb evaluated :: {} -> {}", command.id, it)
                }
            }
        }
    }

}
