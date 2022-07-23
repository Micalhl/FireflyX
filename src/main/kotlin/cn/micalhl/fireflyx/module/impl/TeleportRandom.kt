package cn.micalhl.fireflyx.module.impl

import cn.micalhl.fireflyx.api.FireflyXSettings
import cn.micalhl.fireflyx.module.Module
import cn.micalhl.fireflyx.util.parseString
import org.bukkit.Location
import org.bukkit.Material
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.common.util.random
import taboolib.module.lang.sendLang
import taboolib.platform.util.toBukkitLocation

/**
 * 目前来说比较简陋的随机传送
 */
object TeleportRandom : Module {

    override fun register() {
        command("tpr") {
            execute<ProxyPlayer> { user, _, _ ->
                val location = user.location.clone()
                val minX = user.location.blockX - FireflyXSettings.teleportRandomRadius
                val maxX = user.location.blockX + FireflyXSettings.teleportRandomRadius
                val minZ = user.location.blockZ - FireflyXSettings.teleportRandomRadius
                val maxZ = user.location.blockZ + FireflyXSettings.teleportRandomRadius
                location.x = random(minX, maxX).toDouble()
                location.z = random(minZ, maxZ).toDouble()
                location.y = safeY(location.toBukkitLocation()).toDouble()
                user.teleport(location)
                user.sendLang("teleport-random", location.parseString())
            }
        }
    }

    fun safeY(location: Location): Int {
        val legLoc = location.clone()
        for (y in location.blockY..256 - location.blockY) {
            legLoc.y = y.toDouble()
            val headLoc = legLoc.clone().add(0.0, 1.0, 0.0)
            val downLoc = legLoc.clone().add(0.0, -1.0, 0.0)
            if (headLoc.block.type == Material.AIR && legLoc.block.type == Material.AIR && // 身子不会被方块卡住
                downLoc.block.type != Material.AIR && downLoc.block.type != Material.LAVA // 不会传送到高空, 不会在岩浆湖上面
            ) {
                return y
            }
        }
        return location.blockY
    }
}