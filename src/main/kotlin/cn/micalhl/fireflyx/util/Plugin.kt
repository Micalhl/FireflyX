package cn.micalhl.fireflyx.util

import taboolib.module.nms.nmsClass
import taboolib.platform.BukkitPlugin

fun getTPS(): List<Double> {
    val craftServer = nmsClass("MinecraftServer").getMethod("getServer").invoke(null)
    val recentTps = craftServer.javaClass.getField("recentTps")
    val cast = cast<DoubleArray>(recentTps.get(craftServer))
    val result = arrayListOf<Double>()
    cast.forEach { result.add("%.1f".format(it).toDouble()) }
    return result
}

fun plugin() = BukkitPlugin.getInstance()