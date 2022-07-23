package cn.micalhl.fireflyx.module

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import cn.micalhl.fireflyx.api.FireflyXModules
import cn.micalhl.fireflyx.module.impl.*

object ModuleManager {

    private val modules = listOf(
        Auth, DispenserPatch, Fly, Home, JoinQuitTip, Money, Online, Ping, Spawn, TeleportRandom, Tpa, Tps
    )

    @Awake(LifeCycle.LOAD)
    fun init() {
        modules.filter { FireflyXModules.conf.getBoolean(it.javaClass.simpleName) }
            .forEach { it.register() }
    }
}