package cn.micalhl.fireflyx.module.back

import cn.micalhl.fireflyx.util.parseLocation
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.module.lang.sendLang

object BackCommand {

    fun register() {
        command("back") {
            execute<ProxyPlayer> { user, _, _ ->
                if (Back.dataMap.containsKey(user.uniqueId)) {
                    user.teleport(Back.dataMap[user.uniqueId]!!.parseLocation())
                    user.sendLang("back")
                } else {
                    user.sendLang("back-fail")
                }
            }
        }
    }
}