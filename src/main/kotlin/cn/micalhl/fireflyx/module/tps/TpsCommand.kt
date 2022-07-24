package cn.micalhl.fireflyx.module.tps

import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.command
import taboolib.module.lang.sendLang

object TpsCommand {

    fun register() {
        command("tps") {
            execute<ProxyCommandSender> { user, _, _ ->
                user.sendLang("tps", getTPS().joinToString(", "))
            }
        }
    }
}