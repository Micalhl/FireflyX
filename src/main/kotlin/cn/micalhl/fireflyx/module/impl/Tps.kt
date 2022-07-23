package cn.micalhl.fireflyx.module.impl

import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.command
import taboolib.module.lang.sendLang
import cn.micalhl.fireflyx.module.Module
import cn.micalhl.fireflyx.util.getTPS

object Tps : Module {

    override fun register() {
        command("tps") {
            execute<ProxyCommandSender> { user, _, _ ->
                user.sendLang("tps", getTPS().joinToString(", "))
            }
        }
    }
}