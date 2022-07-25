package cn.micalhl.fireflyx.module.teleportrandom

import cn.micalhl.fireflyx.common.config.Settings
import cn.micalhl.fireflyx.module.back.Back
import cn.micalhl.fireflyx.util.parseString
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.common.platform.function.submit
import taboolib.common.util.random
import taboolib.module.lang.sendLang
import taboolib.platform.util.toBukkitLocation

object TeleportRandomCommand {

    fun register() {
        command("tpr", aliases = listOf("rtp")) {
            execute<ProxyPlayer> { user, _, _ ->
                val location = user.location.clone()
                val minX = user.location.blockX - Settings.teleportRandomRadius
                val maxX = user.location.blockX + Settings.teleportRandomRadius
                val minZ = user.location.blockZ - Settings.teleportRandomRadius
                val maxZ = user.location.blockZ + Settings.teleportRandomRadius
                location.x = random(minX, maxX).toDouble()
                location.z = random(minZ, maxZ).toDouble()
                location.y = location.toBukkitLocation().getSafeY().toDouble()
                user.sendLang("teleport-cd", Settings.teleportCD)
                if (Back.allow) Back.dataMap[user.uniqueId] = user.location.parseString()
                submit(delay = Settings.teleportCD.toLong() * 20L) {
                    user.teleport(location)
                    user.sendLang("teleport-random", location.parseString())
                }
            }
        }
    }
}