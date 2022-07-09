package work.crash.micalhl.fireflyx.module.command

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.command
import taboolib.module.lang.sendLang
import work.crash.micalhl.fireflyx.util.plugin
import kotlin.math.roundToInt

object TPSCommand {

    @Awake(LifeCycle.ENABLE)
    fun register() {
        command("tps") {
            execute<ProxyCommandSender> { user, _, _ ->
                val console = plugin().server.javaClass.getDeclaredField("console")
                val currentTPS = console.type.getDeclaredField("currentTPS")
                val tps = 20.0.coerceAtMost((currentTPS.getDouble(plugin().server) * 10.0).roundToInt() / 10.0)
                user.sendLang("tps", tps)
            }
        }
    }

}