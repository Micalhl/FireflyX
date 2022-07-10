package work.crash.micalhl.fireflyx

import net.milkbowl.vault.economy.Economy
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.platform.BukkitPlugin
import taboolib.platform.compat.isEconomySupported

object FireflyX : Plugin() {

    @Config(migrate = true, autoReload = true)
    lateinit var conf: Configuration

    val plugin by lazy {
        BukkitPlugin.getInstance()
    }

    override fun onEnable() {
        info("Successfully running ExamplePlugin!")
    }

}