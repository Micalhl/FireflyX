package work.crash.micalhl.fireflyx.module.listener

import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.console
import taboolib.common.platform.function.onlinePlayers
import taboolib.module.lang.asLangText
import work.crash.micalhl.fireflyx.module.config.Settings

object JoinQuitTipListener {

    @SubscribeEvent(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun e(e: PlayerJoinEvent) {
        e.joinMessage = console().asLangText("message-join", e.player.name, onlinePlayers().size)
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun e(e: PlayerQuitEvent) {
        e.quitMessage = console().asLangText("message-quit", e.player.name, if (Settings.quitTipFix) onlinePlayers().size - 1 else onlinePlayers().size)
    }

}