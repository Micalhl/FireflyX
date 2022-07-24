package cn.micalhl.fireflyx.module.ping

import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.module.lang.sendLang

object PingCommand {

    fun register() {
        command("ping") {
            execute<ProxyPlayer> { user, _, _ ->
                user.sendLang("ping", user.ping)
            }
        }
    }
}