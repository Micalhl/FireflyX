package work.crash.micalhl.fireflyx.util

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import taboolib.common.platform.ProxyPlayer

fun ProxyPlayer.toOfflinePlayer(): OfflinePlayer = plugin().server.getOfflinePlayer(uniqueId)

fun ProxyPlayer.toBKPlayer(): Player? {
    return if (isOnline()) {
        return plugin().server.getPlayer(uniqueId)
    } else {
        null
    }
}