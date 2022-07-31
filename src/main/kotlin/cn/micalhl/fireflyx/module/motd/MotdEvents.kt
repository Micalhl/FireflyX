package cn.micalhl.fireflyx.module.motd

import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.onlinePlayers
import taboolib.platform.util.asLangTextList
import taboolib.platform.util.sendLang

object MotdEvents {

    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        if (Motd.allow) {
            //e.player.sendLang("motd") {
            //    it?.replace("%player%", e.player.name)?.replace("%online%", onlinePlayers().size.toString())
            //}
            // 暂时用老办法
            val message = e.player.asLangTextList("motd")
            message.map { it.replace("%player%", e.player.name).replace("%online%", onlinePlayers().size.toString()) }.forEach { e.player.sendMessage(it) }
        }
    }
}