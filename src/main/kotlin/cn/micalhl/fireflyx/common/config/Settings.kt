package cn.micalhl.fireflyx.common.config

import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigNode
import taboolib.module.configuration.Configuration
import taboolib.platform.util.toProxyLocation
import cn.micalhl.fireflyx.util.parseString
import cn.micalhl.fireflyx.util.plugin

object Settings {

    @Config(migrate = true, autoReload = true)
    lateinit var conf: Configuration

    @ConfigNode("TeleportCD")
    var teleportCD = 3

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

    @ConfigNode("AuthMsgCD")
    var autoMsgCD = 3L
        get() = field * 20L

    @ConfigNode("AuthKickDelay")
    var autoKickDelay = 20L
        get() = field * 20L

    @ConfigNode("TeleportRandomRadius")
    var teleportRandomRadius = 25000
        private set

    @ConfigNode("AtColor")
    var atColor = "&c"
        private set
}