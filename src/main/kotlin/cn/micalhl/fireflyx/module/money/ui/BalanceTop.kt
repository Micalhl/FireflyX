package cn.micalhl.fireflyx.module.money.ui

import taboolib.common.platform.ProxyPlayer
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.compat.getBalance
import taboolib.platform.compat.hasAccount
import taboolib.platform.util.buildItem
import cn.micalhl.fireflyx.util.plugin
import cn.micalhl.fireflyx.util.toBKPlayer
import taboolib.platform.util.Slots
import java.util.UUID

object BalanceTop {

    fun open(user: ProxyPlayer) {
        user.toBKPlayer()!!.closeInventory()
        user.toBKPlayer()!!.openMenu<Linked<UUID>>("") {
            rows(6)
            slots(Slots.CENTER)
            handLocked(true)
            elements {
                plugin().server.offlinePlayers.map { it.uniqueId }
                    .filter { plugin().server.getOfflinePlayer(it).hasAccount() }
                    .sortedByDescending { plugin().server.getOfflinePlayer(it).getBalance() }
            }
            onGenerate { _, element, index, _ ->
                val player = plugin().server.getOfflinePlayer(element)
                buildItem(XMaterial.PLAYER_HEAD) {
                    name = "&c${index + 1} &f${player.name}"
                    lore.add("&f${player.getBalance()}")
                    colored()
                }
            }
            setNextPage(51) { _, hasNextPage ->
                if (hasNextPage) {
                    buildItem(XMaterial.SPECTRAL_ARROW) {
                        name = "&7->"
                        colored()
                    }
                } else {
                    buildItem(XMaterial.ARROW) {
                        name = "&8->"
                        colored()
                    }
                }
            }
            setPreviousPage(47) { _, hasPreviousPage ->
                if (hasPreviousPage) {
                    buildItem(XMaterial.SPECTRAL_ARROW) {
                        name = "&7<-"
                        colored()
                    }
                } else {
                    buildItem(XMaterial.ARROW) {
                        name = "&8<-"
                        colored()
                    }
                }
            }
        }
    }
}