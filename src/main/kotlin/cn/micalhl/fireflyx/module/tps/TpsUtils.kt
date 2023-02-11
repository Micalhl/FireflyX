package cn.micalhl.fireflyx.module.tps

import taboolib.library.reflex.Reflex.Companion.getProperty
import taboolib.module.nms.nmsClass

fun getTPS(): List<Double> {
    return nmsClass("MinecraftServer").getProperty<Any>("getServer")?.getProperty<DoubleArray>("recentTps")?.toList() ?: emptyList()
}