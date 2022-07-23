package cn.micalhl.fireflyx.module.impl

import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.console
import taboolib.common.platform.function.onlinePlayers
import taboolib.module.lang.asLangText
import cn.micalhl.fireflyx.api.FireflyXSettings
import cn.micalhl.fireflyx.module.Module

object JoinQuitTip : Module {

    var allow = false

    override fun register() {
        allow = true
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun e(e: PlayerJoinEvent) {
        if (allow) {
            e.joinMessage = console().asLangText("message-join", e.player.name, onlinePlayers().size)
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun e(e: PlayerQuitEvent) {
        if (allow) {
            e.quitMessage = console().asLangText(
                "message-quit",
                e.player.name,
                if (FireflyXSettings.quitTipFix) onlinePlayers().size - 1 else onlinePlayers().size
            )
        }
    }
}