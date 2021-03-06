package cn.micalhl.fireflyx.module.money

import cn.micalhl.fireflyx.common.config.Settings
import cn.micalhl.fireflyx.module.money.conversation.CaptchaConversation
import cn.micalhl.fireflyx.module.money.ui.BalanceTop
import cn.micalhl.fireflyx.util.isDouble
import cn.micalhl.fireflyx.util.plugin
import cn.micalhl.fireflyx.util.toBKPlayer
import cn.micalhl.fireflyx.util.toOfflinePlayer
import org.bukkit.conversations.ConversationFactory
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.common.platform.function.getProxyPlayer
import taboolib.common.platform.function.onlinePlayers
import taboolib.common.platform.function.submit
import taboolib.module.lang.sendLang
import taboolib.platform.compat.depositBalance
import taboolib.platform.compat.getBalance
import taboolib.platform.compat.hasAccount
import taboolib.platform.compat.withdrawBalance
import taboolib.platform.util.onlinePlayers

object MoneyCommand {

    fun register() {
        command(name = "balance", aliases = listOf("bal")) {
            dynamic(optional = true, commit = "player") {
                suggestion<ProxyCommandSender>(uncheck = true) { _, _ ->
                    onlinePlayers.filter { it.hasAccount() }.map { it.name }
                }
                execute<ProxyCommandSender> { user, context, _ ->
                    val name = context.argument(0)
                    val target = getProxyPlayer(name)
                    if (target == null) {
                        user.sendLang("common-player-null")
                        return@execute
                    }
                    user.sendLang(
                        "economy-balance-other",
                        target.name,
                        target.toOfflinePlayer().getBalance(),
                        Settings.currencyName
                    )
                }
            }
            execute<ProxyPlayer> { user, _, _ ->
                user.sendLang(
                    "economy-balance-self",
                    user.toOfflinePlayer().getBalance(),
                    Settings.currencyName
                )
            }
        }
        command("pay") {
            dynamic(commit = "player") {
                suggestion<ProxyPlayer>(uncheck = true) { _, _ ->
                    onlinePlayers().map { it.name }
                }
                dynamic(commit = "money") {
                    execute<ProxyPlayer> { user, context, _ ->
                        val name = context.argument(-1)
                        val money = context.argument(0)
                        val player = getProxyPlayer(name)
                        if (player == null) {
                            user.sendLang("common-player-null")
                            return@execute
                        }
                        if (!money.isDouble()) {
                            user.sendLang("economy-pay-failed")
                            return@execute
                        }
                        if (player.toBKPlayer()!!.getBalance() < money.toDouble()) {
                            user.sendLang("economy-pay-not-enough")
                            return@execute
                        }
                        user.sendLang("economy-pay-captcha", name, money, Settings.currencyName)
                        ConversationFactory(plugin())
                            .withFirstPrompt(CaptchaConversation(user) {
                                user.toOfflinePlayer().withdrawBalance(money.toDouble())
                                player.toOfflinePlayer().depositBalance(money.toDouble())
                                submit(delay = 1L, async = true) {
                                    user.sendLang("economy-pay-success", name, money, Settings.currencyName)
                                    player.sendLang(
                                        "economy-pay-receive",
                                        user.name,
                                        money,
                                        Settings.currencyName
                                    )
                                }
                            })
                            .withLocalEcho(false)
                            .buildConversation(user.toBKPlayer()!!)
                            .begin()
                    }
                }
            }
        }
        command(name = "balancetop", aliases = listOf("baltop")) {
            execute<ProxyPlayer> { user, _, _ ->
                BalanceTop.open(user)
            }
        }
    }
}