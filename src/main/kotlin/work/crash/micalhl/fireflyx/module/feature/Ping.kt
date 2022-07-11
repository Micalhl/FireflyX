package work.crash.micalhl.fireflyx.module.feature

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.module.lang.sendLang

object Ping {

    @Awake(LifeCycle.ACTIVE)
    fun register() {
        command("ping") {
            execute<ProxyPlayer> { user, _, _ ->
                user.sendLang("ping", user.ping)
            }
        }
    }

}