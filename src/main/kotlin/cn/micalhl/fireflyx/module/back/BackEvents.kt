package cn.micalhl.fireflyx.module.back

import cn.micalhl.fireflyx.util.parseString
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.event.player.PlayerTeleportEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.sendLang
import taboolib.platform.util.toProxyLocation

object BackEvents {

    /**
     * 记录上一处位置
     */
    @SubscribeEvent
    fun e(e: PlayerTeleportEvent) {
        if (Back.allow && e.to != null) {
            Back.dataMap[e.player.uniqueId] = e.to!!.toProxyLocation().parseString()
        }
    }

    /**
     * 提示
     */
    @SubscribeEvent
    fun e(e: PlayerRespawnEvent) {
        if (Back.allow) {
            e.player.sendLang("back-death")
        }
    }
}