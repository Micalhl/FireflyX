package cn.micalhl.fireflyx.module.money

import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.console
import taboolib.module.lang.sendLang
import taboolib.platform.compat.createAccount
import taboolib.platform.compat.hasAccount

object MoneyEvents {

    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        if (Money.allow && !e.player.hasAccount()) {
            e.player.createAccount()
            console().sendLang("economy-account-create", e.player.uniqueId.toString())
        }
    }
}