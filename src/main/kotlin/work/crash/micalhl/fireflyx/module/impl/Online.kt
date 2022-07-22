package work.crash.micalhl.fireflyx.module.impl

import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.command
import taboolib.common.platform.function.onlinePlayers
import taboolib.module.lang.sendLang
import work.crash.micalhl.fireflyx.module.Module
import work.crash.micalhl.fireflyx.util.plugin

object Online : Module {

    override fun register() {
        command("online", aliases = listOf("list", "players")) {
            execute<ProxyCommandSender> { user, _, _ ->
                user.sendLang("online-players", onlinePlayers().size, plugin().server.maxPlayers)
                if (onlinePlayers().isNotEmpty()) {
                    user.sendLang("online-players-list", onlinePlayers().map { "&c${it.name}" }.joinToString { "&f, " })
                }
            }
        }
    }
}