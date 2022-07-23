package cn.micalhl.fireflyx.api

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.console
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.module.lang.sendLang
import cn.micalhl.fireflyx.util.plugin

object FireflyXModules {

    @Config(value = "modules.yml", migrate = true, autoReload = true)
    lateinit var conf: Configuration
        private set

    @Awake(LifeCycle.ENABLE)
    fun onReload() {
        conf.onReload {
            console().sendLang("modules-reload-server")
            plugin().server.shutdown()
        }
    }
}