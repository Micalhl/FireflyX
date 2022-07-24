package cn.micalhl.fireflyx.module.onekeysellitem

import cn.micalhl.fireflyx.util.toBKPlayer
import org.bukkit.Material
import taboolib.common.platform.ProxyPlayer
import taboolib.platform.compat.depositBalance
import taboolib.platform.util.isNotAir

fun ProxyPlayer.oneKeySellItem(name: String, price: Double): Int {
    val player = toBKPlayer()!!
    var i = 0
    for (item in player.inventory.contents) {
        if (item != null && item.isNotAir() && Material.getMaterial(name).isNotAir() && item.type == Material.getMaterial(name)) {
            i += item.amount
            item.amount = 0
        }
    }
    val money = price * i
    player.depositBalance(money)
    return i
}