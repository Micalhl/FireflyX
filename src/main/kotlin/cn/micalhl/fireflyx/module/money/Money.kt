package cn.micalhl.fireflyx.module.money

import cn.micalhl.fireflyx.module.Module
import cn.micalhl.fireflyx.module.money.compat.Vault
import cn.micalhl.fireflyx.util.plugin
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.ServicePriority
import taboolib.common.platform.function.console
import taboolib.module.lang.sendLang

object Money : Module {

    override var allow = false

    override fun init() {
        super.init()
        MoneyCommand.register()
    }

    fun hook() {
        if (allow) {
            if (plugin().server.pluginManager.getPlugin("Vault") == null) {
                console().sendLang("economy-no-vault")
                allow = false
                return
            }
            val provider = Vault()
            plugin().server.servicesManager.register(Economy::class.java, provider, plugin(), ServicePriority.Normal)
            console().sendLang("plugin-vault-hooked")
        }
    }

    fun unhook() {
        plugin().server.servicesManager.unregisterAll(plugin())
    }
}