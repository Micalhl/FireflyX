package cn.micalhl.fireflyx.module.onekeysellitem

import cn.micalhl.fireflyx.common.config.Settings
import cn.micalhl.fireflyx.util.isDouble
import cn.micalhl.fireflyx.util.toBKPlayer
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.module.lang.sendLang
import taboolib.platform.compat.getBalance

object OneKeySellItemCommand {

    fun register() {
        command("onekeysellitem") {
            dynamic(commit = "material") {
                suggestion<ProxyPlayer> { user, _ -> Material.values().map { it.name } }
                dynamic(commit = "price") {
                    execute<ProxyPlayer> { user, context, _ ->
                        val name = context.argument(-1)
                        val price = context.argument(0)
                        if (Material.getMaterial(name) == null) {
                            user.sendLang("onekeysellitem-material-error")
                            return@execute
                        }
                        if (!price.isDouble()) {
                            user.sendLang("common-int-argument-error")
                            return@execute
                        }
                        val result = user.oneKeySellItem(name, price.toDouble())
                        user.sendLang("onekeysellitem", result, ItemStack(Material.getMaterial(name)!!), price.toDouble() * result, user.toBKPlayer()!!.getBalance(), Settings.currencyName)
                    }
                }
            }
        }
    }
}