package cn.micalhl.fireflyx.module.teleportrandom

import org.bukkit.Location
import org.bukkit.Material

fun Location.getSafeY(): Int {
    val legLoc = clone()
    for (y in 256 downTo 0) { // 从 256 向下遍历, 这样传送到矿洞的几率会大大减小
        legLoc.y = y.toDouble()
        val headLoc = legLoc.clone().add(0.0, 1.0, 0.0)
        val downLoc = legLoc.clone().add(0.0, -1.0, 0.0)
        if (headLoc.block.type == Material.AIR && legLoc.block.type == Material.AIR && // 身子不会被方块卡住
            downLoc.block.type != Material.AIR && downLoc.block.type != Material.LAVA // 不会传送到高空, 不会在岩浆湖上面
        ) {
            return y
        }
    }
    return blockY
}