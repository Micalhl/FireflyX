package cn.micalhl.fireflyx.common.services

import cn.micalhl.fireflyx.module.ModuleManager
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.Platform
import taboolib.common.platform.function.pluginVersion
import taboolib.module.metrics.Metrics
import taboolib.module.metrics.charts.AdvancedPie

object Metrics {

    @Awake(LifeCycle.ENABLE)
    fun init() {
        val metrics = Metrics(15924, pluginVersion, Platform.BUKKIT)
        metrics.addCustomChart(AdvancedPie("modules") {
            HashMap<String, Int>().also { map ->
                ModuleManager.loaded.forEach {
                    map[it] = (map[it] ?: 0) + 1
                }
            }
        })
    }
}