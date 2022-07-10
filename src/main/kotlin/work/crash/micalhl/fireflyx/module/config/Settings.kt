package work.crash.micalhl.fireflyx.module.config

import taboolib.module.configuration.ConfigNode

object Settings {

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

}