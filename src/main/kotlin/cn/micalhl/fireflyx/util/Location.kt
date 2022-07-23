package cn.micalhl.fireflyx.util

import taboolib.common.util.Location
import taboolib.common5.Coerce

fun String.parseLocation(): Location {
    val (world, loc) = split("~", limit = 2)
    val (x, y, z) = loc.split(",", limit = 3).map { it.toDouble() }

    return Location(world, x, y, z)
}

fun Location.parseString(): String {
    val world = this.world
    val x = Coerce.format(x)
    val y = Coerce.format(y)
    val z = Coerce.format(z)

    return "$world~$x,$y,$z"
}