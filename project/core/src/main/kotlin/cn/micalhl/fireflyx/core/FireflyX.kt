package cn.micalhl.fireflyx.core

import taboolib.platform.BukkitPlugin

/**
 * 这是你插件对外开放的主类
 * 可以开放一些接口
 *
 * 默认情况下这个模块不包含 Bukkit 核心
 * 如果你的插件没有跨平台需求，可以在这个项目中引入 Bukkit 核心，以使用 Bukkit 的 API
 */
object FireflyX {

    val modules = hashMapOf<String, String>()

    val plugin by lazy {
        BukkitPlugin.getInstance()
    }
}