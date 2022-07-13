package work.crash.micalhl.fireflyx.module.impl

import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.module.lang.sendLang
import work.crash.micalhl.fireflyx.module.Module

object Ping : Module {

    override fun register() {
        command("ping") {
            execute<ProxyPlayer> { user, _, _ ->
                user.sendLang("ping", user.ping)
            }
        }
    }

}