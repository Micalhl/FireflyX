package work.crash.micalhl.fireflyx.module.listener

import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.console
import taboolib.module.lang.sendLang
import taboolib.platform.compat.createAccount
import taboolib.platform.compat.hasAccount

object EconomyListener {

    @SubscribeEvent(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun e(e: PlayerJoinEvent) {
        if (!e.player.hasAccount()) {
            e.player.createAccount()
            console().sendLang("economy-account-create", e.player.uniqueId.toString())
        }
    }

}