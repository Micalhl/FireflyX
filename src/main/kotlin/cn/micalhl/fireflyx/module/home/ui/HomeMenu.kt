package cn.micalhl.fireflyx.module.home.ui

import taboolib.common.platform.ProxyPlayer
import taboolib.common5.Coerce
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.util.buildItem
import taboolib.platform.util.inventoryCenterSlots
import cn.micalhl.fireflyx.api.FireflyXAPI
import cn.micalhl.fireflyx.module.home.database.HomeDatabase
import cn.micalhl.fireflyx.util.parseLocation
import cn.micalhl.fireflyx.util.toBKPlayer

object HomeMenu {

    fun open(user: ProxyPlayer) {
        user.toBKPlayer()!!.closeInventory()
        user.toBKPlayer()!!.openMenu<Linked<HomeDatabase.Home>>("") {
            rows(6)
            slots(inventoryCenterSlots)
            handLocked(true)
            elements {
                FireflyXAPI.homeDatabase.get(user.uniqueId)
            }
            onGenerate { _, element, _, _ ->
                val location = element.location.parseLocation()
                buildItem(XMaterial.RED_BED) {
                    name = "&3&l${element.name}"
                    lore.addAll(listOf(
                        "&f${location.world!!}",
                        "&f${Coerce.format(location.x)}",
                        "&f${Coerce.format(location.y)}",
                        "&f${Coerce.format(location.z)}"
                    ))
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