package work.crash.micalhl.fireflyx.module.impl

import org.bukkit.conversations.ConversationFactory
import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.console
import taboolib.common.platform.function.getProxyPlayer
import taboolib.common.platform.function.onlinePlayers
import taboolib.common.platform.function.submit
import taboolib.module.lang.sendLang
import taboolib.platform.compat.*
import taboolib.platform.util.onlinePlayers
import work.crash.micalhl.fireflyx.api.FireflyXSettings
import work.crash.micalhl.fireflyx.internal.conversation.CaptchaConversation
import work.crash.micalhl.fireflyx.internal.ui.BalanceTop
import work.crash.micalhl.fireflyx.module.Module
import work.crash.micalhl.fireflyx.util.isDouble
import work.crash.micalhl.fireflyx.util.plugin
import work.crash.micalhl.fireflyx.util.toBKPlayer
import work.crash.micalhl.fireflyx.util.toOfflinePlayer

object Money : Module {

    var allow = false

    override fun register() {
        allow = true
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
                    user.sendLang("economy-balance-other", target.name, target.toOfflinePlayer().getBalance(), FireflyXSettings.currencyName)
                }
            }
            execute<ProxyPlayer> { user, _, _ ->
                user.sendLang("economy-balance-self", user.toOfflinePlayer().getBalance(), FireflyXSettings.currencyName)
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
                        user.sendLang("economy-pay-captcha", name, money, FireflyXSettings.currencyName)
                        ConversationFactory(plugin())
                            .withFirstPrompt(CaptchaConversation(user) {
                                user.toOfflinePlayer().withdrawBalance(money.toDouble())
                                player.toOfflinePlayer().depositBalance(money.toDouble())
                                submit(delay = 1L, async = true) {
                                    user.sendLang("economy-pay-success", name, money, FireflyXSettings.currencyName)
                                    player.sendLang("economy-pay-receive", user.name, money, FireflyXSettings.currencyName)
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

    @SubscribeEvent(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun e(e: PlayerJoinEvent) {
        if (allow && !e.player.hasAccount()) {
            e.player.createAccount()
            console().sendLang("economy-account-create", e.player.uniqueId.toString())
        }
    }

}