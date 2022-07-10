package work.crash.micalhl.fireflyx.module.command

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.common.platform.function.getProxyPlayer
import taboolib.module.lang.sendLang
import taboolib.platform.compat.getBalance
import taboolib.platform.compat.hasAccount
import taboolib.platform.util.onlinePlayers
import work.crash.micalhl.fireflyx.util.plugin
import work.crash.micalhl.fireflyx.util.toOfflinePlayer

object MoneyCommand {

    @Awake(LifeCycle.ACTIVE)
    fun register() {
        command(name = "balance", aliases = listOf("bal")) {
            dynamic(optional = true, commit = "player") {
                suggestion<ProxyCommandSender> { _, _ ->
                    onlinePlayers.filter { it.hasAccount() }.map { it.name }
                }
                execute<ProxyCommandSender> { user, context, _ ->
                    val name = context.argument(0)
                    val target = getProxyPlayer(name)
                    if (target == null) {
                        user.sendLang("common-player-null")
                        return@execute
                    }
                    user.sendLang("economy-balance-other", target.name, target.toOfflinePlayer().getBalance())
                }
            }
            execute<ProxyPlayer> { user, _, _ ->
                user.sendLang("economy-balance-self", user.toOfflinePlayer().getBalance())
            }
        }
    }

}