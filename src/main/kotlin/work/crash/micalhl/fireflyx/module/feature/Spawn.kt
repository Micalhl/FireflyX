package work.crash.micalhl.fireflyx.module.feature

import org.bukkit.event.player.PlayerRespawnEvent
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.lang.sendLang
import taboolib.platform.util.toBukkitLocation
import work.crash.micalhl.fireflyx.api.FireflyXSettings
import work.crash.micalhl.fireflyx.util.parseLocation
import work.crash.micalhl.fireflyx.util.parseString

object Spawn {

    @Awake(LifeCycle.ACTIVE)
    fun register() {
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
        if (!(e.isAnchorSpawn && e.isBedSpawn)) {
            e.respawnLocation = FireflyXSettings.spawn.parseLocation().toBukkitLocation()
        }
    }

}