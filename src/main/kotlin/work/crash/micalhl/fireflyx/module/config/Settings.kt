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

}