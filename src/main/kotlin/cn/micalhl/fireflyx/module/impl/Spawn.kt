package cn.micalhl.fireflyx.module.impl

import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.lang.sendLang
import taboolib.platform.util.toBukkitLocation
import cn.micalhl.fireflyx.api.FireflyXSettings
import cn.micalhl.fireflyx.module.Module
import cn.micalhl.fireflyx.util.parseLocation
import cn.micalhl.fireflyx.util.parseString

object Spawn : Module {

    var allow = false
    override fun register() {
        allow = true
        command("spawn") {
            execute<ProxyPlayer> { user, _, _ ->
                user.teleport(FireflyXSettings.spawn.parseLocation())
            }
        }
        command("setspawn") {
            execute<ProxyPlayer> { user, _, _ ->
                FireflyXSettings.spawn = user.location.parseString()
                user.sendLang("setspawn")
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun e(e: PlayerRespawnEvent) {
        if (allow && !(e.isAnchorSpawn && e.isBedSpawn)) {
            e.respawnLocation = FireflyXSettings.spawn.parseLocation().toBukkitLocation()
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun e(e: PlayerJoinEvent) {
        if (!e.player.hasPlayedBefore()) {
            e.player.teleport(FireflyXSettings.spawn.parseLocation().toBukkitLocation())
        }
    }
}