package work.crash.micalhl.fireflyx.module.impl

import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.command
import taboolib.module.lang.sendLang
import work.crash.micalhl.fireflyx.module.Module
import work.crash.micalhl.fireflyx.util.getTPS

object Tps : Module {

    override fun register() {
        command("tps") {
            execute<ProxyCommandSender> { user, _, _ ->
                user.sendLang("tps", getTPS().joinToString(", "))
            }
        }
    }

}