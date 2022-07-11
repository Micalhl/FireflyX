package work.crash.micalhl.fireflyx.api

import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigNode
import taboolib.module.configuration.Configuration
import taboolib.platform.util.toProxyLocation
import work.crash.micalhl.fireflyx.util.parseString
import work.crash.micalhl.fireflyx.util.plugin

object FireflyXSettings {

    @Config(migrate = true, autoReload = true)
    lateinit var conf: Configuration

    @ConfigNode("TeleportCD")
    var teleportCD = 3
        private set

    @ConfigNode("TeleportTime")
    var teleportTime = 120
        private set

    @ConfigNode("QuitTipFix")
    var quitTipFix = true
        private set

    @ConfigNode("FirstJoinMoney")
    var firstJoinMoney = 100.0
        private set

    @ConfigNode("CurrencyName")
    var currencyName = "硬币"
        private set

    @ConfigNode("CaptchaIndex")
    var captchaIndex = 6
        private set

    @ConfigNode("Spawn")
    var spawn = "FirstEnable"
        get() = if (field == "FirstEnable") plugin().server.getWorld("world")!!.spawnLocation.toProxyLocation()
            .parseString() else field

}