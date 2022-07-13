package work.crash.micalhl.fireflyx.module

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import work.crash.micalhl.fireflyx.api.FireflyXModules
import work.crash.micalhl.fireflyx.module.impl.*

object ModuleManager {

    private val modules = listOf(
        Fly, Home, JoinQuitTip, Money, Ping, Ping, Spawn, Tpa, Tps
    )

    @Awake(LifeCycle.ACTIVE)
    fun init() {
        modules.filter { FireflyXModules.conf.getBoolean(it.javaClass.simpleName) }.forEach { it.register() }
    }

}