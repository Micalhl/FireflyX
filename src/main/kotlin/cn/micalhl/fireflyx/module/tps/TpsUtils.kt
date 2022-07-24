package cn.micalhl.fireflyx.module.tps

import cn.micalhl.fireflyx.util.cast
import taboolib.module.nms.nmsClass

fun getTPS(): List<Double> {
    val craftServer = nmsClass("MinecraftServer").getMethod("getServer").invoke(null)
    val recentTps = craftServer.javaClass.getField("recentTps")
    val cast = cast<DoubleArray>(recentTps.get(craftServer))
    val result = arrayListOf<Double>()
    cast.forEach { result.add("%.1f".format(it).toDouble()) }
    return result
}