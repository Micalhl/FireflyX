package work.crash.micalhl.fireflyx.module.command

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.common.platform.function.getProxyPlayer
import taboolib.common.platform.function.onlinePlayers
import taboolib.module.lang.sendLang

object FlyCommand {

    @Awake(LifeCycle.ACTIVE)
    fun register() {
        command(name = "fly", permission = "fireflyx.command.fly/") {
            dynamic(optional = true, commit = "player") {
                suggestion<ProxyCommandSender> { _, _ ->
                    onlinePlayers().map { it.name }
                }
                execute<ProxyCommandSender> { user, context, _ ->
                    val name = context.argument(0)
                    val player = getProxyPlayer(name)
                    if (player == null) {
                        user.sendLang("common-player-null")
                        return@execute
                    }
                    if (player.allowFlight) {
                        player.allowFlight = false
                        user.sendLang("fly-set-disable", name)
                        player.sendLang("fly-disable-other", user.name)
                    } else {
                        player.allowFlight = true
                        user.sendLang("fly-set-enable", name)
                        player.sendLang("fly-enable-other", user.name)
                    }
                }
            }
            execute<ProxyPlayer> { user, _, _ ->
                if (user.allowFlight) {
                    user.allowFlight = false
                    user.sendLang("fly-disable")
                } else {
                    user.allowFlight = true
                    user.sendLang("fly-enable")
                }
            }
        }
    }

}