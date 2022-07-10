package work.crash.micalhl.fireflyx.util

import org.bukkit.OfflinePlayer
import taboolib.common.platform.ProxyPlayer

fun ProxyPlayer.toOfflinePlayer(): OfflinePlayer = plugin().server.getOfflinePlayer(uniqueId)