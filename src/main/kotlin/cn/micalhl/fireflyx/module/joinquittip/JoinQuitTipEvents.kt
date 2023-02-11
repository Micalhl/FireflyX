package cn.micalhl.fireflyx.module.joinquittip

import cn.micalhl.fireflyx.common.config.Settings
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.console
import taboolib.common.platform.function.onlinePlayers
import taboolib.module.lang.asLangText

object JoinQuitTipEvents {

    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        if (JoinQuitTip.allow) {
            e.joinMessage = console().asLangText("message-join", e.player.name, onlinePlayers().size)
        }
    }

    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        if (JoinQuitTip.allow) {
            e.quitMessage = console().asLangText(
                "message-quit",
                e.player.name,
                if (Settings.quitTipFix) onlinePlayers().size - 1 else onlinePlayers().size
            )
        }
    }
}