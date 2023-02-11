package cn.micalhl.fireflyx.module.spawn

import cn.micalhl.fireflyx.common.config.Settings
import cn.micalhl.fireflyx.util.parseLocation
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.toBukkitLocation

object SpawnEvents {

    @SubscribeEvent
    fun e(e: PlayerRespawnEvent) {
        if (Spawn.allow && !(e.isAnchorSpawn && e.isBedSpawn)) {
            e.respawnLocation = Settings.spawn.parseLocation().toBukkitLocation()
        }
    }

    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        if (!e.player.hasPlayedBefore()) {
            e.player.teleport(Settings.spawn.parseLocation().toBukkitLocation())
        }
    }
}