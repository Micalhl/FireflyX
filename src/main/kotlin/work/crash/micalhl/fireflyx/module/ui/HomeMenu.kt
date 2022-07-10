package work.crash.micalhl.fireflyx.module.ui

import taboolib.common.platform.ProxyPlayer
import taboolib.common5.Coerce
import taboolib.library.xseries.XMaterial
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Linked
import taboolib.platform.util.buildItem
import taboolib.platform.util.inventoryCenterSlots
import work.crash.micalhl.fireflyx.api.FireflyXAPI
import work.crash.micalhl.fireflyx.module.database.DatabaseHome
import work.crash.micalhl.fireflyx.util.parseLocation
import work.crash.micalhl.fireflyx.util.toBKPlayer

object HomeMenu {

    fun open(user: ProxyPlayer) {
        user.toBKPlayer()!!.closeInventory()
        user.toBKPlayer()!!.openMenu<Linked<DatabaseHome.Home>>("") {
            rows(6)
            slots(inventoryCenterSlots)
            handLocked(true)
            elements {
                FireflyXAPI.databaseHome.get(user.uniqueId)
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