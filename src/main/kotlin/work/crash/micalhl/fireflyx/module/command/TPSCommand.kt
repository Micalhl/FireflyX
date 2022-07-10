package work.crash.micalhl.fireflyx.module.command

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.command
import taboolib.module.lang.sendLang
import work.crash.micalhl.fireflyx.util.getTPS

object TPSCommand {

    @Awake(LifeCycle.ACTIVE)
    fun register() {
        command("tps") {
            execute<ProxyCommandSender> { user, _, _ ->
                user.sendLang("tps", getTPS().joinToString(", "))
            }
        }
    }

}