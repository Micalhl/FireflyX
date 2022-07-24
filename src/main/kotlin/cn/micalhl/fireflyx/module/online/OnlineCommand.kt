package cn.micalhl.fireflyx.module.online

import cn.micalhl.fireflyx.util.plugin
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.command
import taboolib.common.platform.function.onlinePlayers
import taboolib.module.chat.colored
import taboolib.module.lang.sendLang

object OnlineCommand {

    fun register() {
        command("online", aliases = listOf("list", "players")) {
            execute<ProxyCommandSender> { user, _, _ ->
                user.sendLang("online-players", onlinePlayers().size, plugin().server.maxPlayers)
                if (onlinePlayers().isNotEmpty()) {
                    user.sendLang("online-players-list", onlinePlayers().map { "&c${it.name}" }.joinToString("&f, ").colored())
                }
            }
        }
    }
}