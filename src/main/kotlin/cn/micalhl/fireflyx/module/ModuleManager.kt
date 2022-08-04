package cn.micalhl.fireflyx.module

import cn.micalhl.fireflyx.common.config.Modules
import cn.micalhl.fireflyx.module.Module
import cn.micalhl.fireflyx.module.at.At
import cn.micalhl.fireflyx.module.auth.Auth
import cn.micalhl.fireflyx.module.back.Back
import cn.micalhl.fireflyx.module.dispenserpatch.DispenserPatch
import cn.micalhl.fireflyx.module.fly.Fly
import cn.micalhl.fireflyx.module.home.Home
import cn.micalhl.fireflyx.module.joinquittip.JoinQuitTip
import cn.micalhl.fireflyx.module.money.Money
import cn.micalhl.fireflyx.module.motd.Motd
import cn.micalhl.fireflyx.module.onekeysellitem.OneKeySellItem
import cn.micalhl.fireflyx.module.online.Online
import cn.micalhl.fireflyx.module.ping.Ping
import cn.micalhl.fireflyx.module.spawn.Spawn
import cn.micalhl.fireflyx.module.stops.Stops
import cn.micalhl.fireflyx.module.teleportrandom.TeleportRandom
import cn.micalhl.fireflyx.module.tpa.Tpa
import cn.micalhl.fireflyx.module.tps.Tps
import taboolib.common.platform.function.console
import taboolib.module.configuration.util.getStringColored
import taboolib.module.lang.sendLang

object ModuleManager {

    private val modules = arrayListOf(
        At,
        Auth,
        Back,
        DispenserPatch,
        Fly,
        Home,
        JoinQuitTip,
        Money,
        Motd,
        OneKeySellItem,
        Online,
        Ping,
        Spawn,
        Stops,
        TeleportRandom,
        Tpa,
        Tps
    )

    val loaded = arrayListOf<String>()

    fun init() {
        modules.filter { Modules.conf.getBoolean("${it.javaClass.simpleName}.enable") }.forEach {
            it.init()
            if (it.allow) {
                loaded.add(it.javaClass.simpleName)
            }
        }
        loaded.forEach {
            console().sendLang(
                "plugin-module-enabled", Modules.conf.getStringColored("${it}.name") ?: it
            )
        }
        with(modules.map { it.javaClass.simpleName }.toMutableList()) {
            removeAll(loaded)
            forEach { console().sendLang("plugin-module-disabled", Modules.conf.getStringColored("${it}.name") ?: it) }
        }
    }
}