package cn.micalhl.fireflyx

import cn.micalhl.fireflyx.module.ModuleManager
import cn.micalhl.fireflyx.module.money.Money
import cn.micalhl.fireflyx.util.plugin
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.common.platform.function.pluginVersion
import taboolib.module.lang.sendLang

object FireflyX : Plugin() {

    override fun onEnable() {
        console().sendLang("plugin-logo")
        console().sendLang("plugin-loading", plugin().server.version)
        ModuleManager.init()
        Money.hook()
        console().sendLang("plugin-enabled", pluginVersion)
    }

    override fun onDisable() {
        Money.unhook()
    }
}