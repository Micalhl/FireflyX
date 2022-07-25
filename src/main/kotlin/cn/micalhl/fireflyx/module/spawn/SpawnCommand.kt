package cn.micalhl.fireflyx.module.spawn

import cn.micalhl.fireflyx.common.config.Settings
import cn.micalhl.fireflyx.module.back.Back
import cn.micalhl.fireflyx.util.parseLocation
import cn.micalhl.fireflyx.util.parseString
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.module.lang.sendLang

object SpawnCommand {

    fun register() {
        command("spawn") {
            execute<ProxyPlayer> { user, _, _ ->
                if (Back.allow) Back.dataMap[user.uniqueId] = user.location.parseString()
                user.teleport(Settings.spawn.parseLocation())
                user.sendLang("teleport-spawn")
            }
        }
        command("setspawn") {
            execute<ProxyPlayer> { user, _, _ ->
                Settings.spawn = user.location.parseString()
                user.sendLang("setspawn")
            }
        }
    }
}