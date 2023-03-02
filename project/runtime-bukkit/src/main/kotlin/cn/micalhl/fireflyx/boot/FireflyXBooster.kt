package cn.micalhl.fireflyx.boot

import cn.micalhl.fireflyx.core.FireflyX
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.common.platform.function.pluginVersion
import taboolib.module.lang.sendLang

/**
 * 这是你的插件在 Bukkit 平台运行的基础
 * 一般情况下你不需要修改这个类
 */
object FireflyXBooster : Plugin() {

    override fun onEnable() {
        console().sendLang("plugin-logo")
        console().sendLang("plugin-loading", FireflyX.plugin.server.version)
        FireflyX.modules.forEach { console().sendLang("plugin-module-enabled", it.value) }
        console().sendLang("plugin-enabled", pluginVersion)
    }
}