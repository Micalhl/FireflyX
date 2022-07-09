package work.crash.micalhl.fireflyx.util

import taboolib.module.nms.nmsClass

fun getTPS(): List<Double> {
    val craftServer = nmsClass("MinecraftServer").getMethod("getServer").invoke(null)
    val recentTps = craftServer.javaClass.getField("recentTps")
    return cast<DoubleArray>(recentTps.get(craftServer)).onEach { "%.1f".format(it) }.map { it }
}