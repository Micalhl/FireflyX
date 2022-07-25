package cn.micalhl.fireflyx.module.back

import cn.micalhl.fireflyx.util.parseString
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerRespawnEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.sendLang
import taboolib.platform.util.toProxyLocation

object BackEvents {

    @SubscribeEvent
    fun e(e: PlayerDeathEvent) {
        if (Back.allow) {
            Back.dataMap[e.entity.uniqueId] = e.entity.location.toProxyLocation().parseString()
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