package cn.micalhl.fireflyx.module.stops

import cn.micalhl.fireflyx.util.plugin
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.command
import taboolib.common.platform.function.console
import taboolib.common.platform.function.onlinePlayers
import taboolib.common.platform.function.submit
import taboolib.module.chat.colored
import taboolib.module.kether.isInt
import taboolib.module.lang.asLangText
import taboolib.module.lang.sendLang

object StopsCommand {

    var stopping = false

    fun register() {
        command("stops") {
            literal("cancel") {
                execute<ProxyCommandSender> { user, _, _ ->
                    if (!stopping) {
                        user.sendLang("stops-is-cancelled")
                        return@execute
                    }
                    stopping = false
                    user.sendLang("stops-cancelled")
                }
            }
            dynamic(commit = "time", optional = true) {
                suggestion<ProxyCommandSender>(uncheck = true) { _, _ ->
                    val list = arrayListOf<String>()
                    (1..60).forEach { list.add(it.toString()) }
                    return@suggestion list
                }
                execute<ProxyCommandSender> { user, context, _ ->
                    val time = context.argument(0)
                    if (time.isInt()) {
                        if (time.toInt() < 1) {
                            user.sendLang("stops-time-short")
                            return@execute
                        }
                        if (stopping) {
                            user.sendLang("stops-is-running")
                            return@execute
                        }
                        stopping = true
                        user.sendLang("stops-run", time)
                        schedule(time.toInt())
                    } else {
                        user.sendLang("common-int-argument-error")
                    }
                }
            }
            execute<ProxyCommandSender> { user, _, _ ->
                stopping = true
                user.sendLang("stops-run", 60)
                schedule(60)
            }
        }
    }

    private fun schedule(period: Int) {
        var time = period
        submit(period = 20L) {
            if (!stopping) cancel()
            plugin().server.broadcastMessage(console().asLangText("stops-message", period).colored())
            time--
            if (time == 0) {
                onlinePlayers().forEach { it.kick(plugin().server.shutdownMessage ?: "Server closed") }
                plugin().server.shutdown()
            }
        }
    }
}