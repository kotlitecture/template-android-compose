package core.ui.misc.extension

import org.tinylog.Logger

fun traceRecompose(id: String, vararg args: Any?) {
    Logger.info("traceRecompose :: id={}, args={}", id,args.toList())
}