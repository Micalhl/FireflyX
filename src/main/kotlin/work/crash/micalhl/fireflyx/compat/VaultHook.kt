package work.crash.micalhl.fireflyx.compat

import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.ServicePriority
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import work.crash.micalhl.fireflyx.util.plugin

object VaultHook {

    @Awake(LifeCycle.ENABLE)
    fun hook() {
        val provider = Vault()
        plugin().server.servicesManager.register(Economy::class.java, provider, plugin(), ServicePriority.Normal)
    }

    @Awake(LifeCycle.DISABLE)
    fun unhook() {
        plugin().server.servicesManager.unregisterAll(plugin())
    }

}