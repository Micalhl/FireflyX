package cn.micalhl.fireflyx.module

import cn.micalhl.fireflyx.common.config.Modules
import cn.micalhl.fireflyx.module.auth.Auth
import cn.micalhl.fireflyx.module.dispenserpatch.DispenserPatch
import cn.micalhl.fireflyx.module.fly.Fly
import cn.micalhl.fireflyx.module.home.Home
import cn.micalhl.fireflyx.module.joinquittip.JoinQuitTip
import cn.micalhl.fireflyx.module.money.Money
import cn.micalhl.fireflyx.module.onekeysellitem.OneKeySellItem
import cn.micalhl.fireflyx.module.online.Online
import cn.micalhl.fireflyx.module.ping.Ping
import cn.micalhl.fireflyx.module.spawn.Spawn
import cn.micalhl.fireflyx.module.teleportrandom.TeleportRandom
import cn.micalhl.fireflyx.module.tpa.Tpa
import cn.micalhl.fireflyx.module.tps.Tps
import taboolib.common.platform.function.console
import taboolib.module.configuration.util.getStringColored
import taboolib.module.lang.sendLang

object ModuleManager {

    private val modules = listOf(
        Auth, DispenserPatch, Fly, Home, JoinQuitTip, Money, OneKeySellItem, Online, Ping, Spawn, TeleportRandom, Tpa, Tps
    )

    fun init() {
        modules.filter { Modules.conf.getBoolean("${it.javaClass.simpleName}.enable") }.forEach {
                it.init()
                console().sendLang(
                    "plugin-module-enabled",
                    Modules.conf.getStringColored("${it.javaClass.simpleName}.name") ?: it.javaClass.simpleName
                )
            }
        modules.filter { !Modules.conf.getBoolean("${it.javaClass.simpleName}.enable") }.forEach {
            console().sendLang(
                "plugin-module-enabled",
                Modules.conf.getStringColored("${it.javaClass.simpleName}.name") ?: it.javaClass.simpleName
            )
        }
    }
}