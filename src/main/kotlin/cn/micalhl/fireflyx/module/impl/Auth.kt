package cn.micalhl.fireflyx.module.impl

import cn.micalhl.fireflyx.api.FireflyXAPI
import cn.micalhl.fireflyx.api.FireflyXSettings
import cn.micalhl.fireflyx.module.Module
import cn.micalhl.fireflyx.util.plugin
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerTeleportEvent
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.console
import taboolib.common.platform.function.onlinePlayers
import taboolib.common.platform.function.submit
import taboolib.module.lang.asLangText
import taboolib.module.lang.sendLang
import taboolib.platform.event.PlayerJumpEvent
import taboolib.platform.util.sendLang
import java.security.MessageDigest
import java.util.UUID

object Auth : Module {

    var allow = false
    val login = arrayListOf<UUID>()

    override fun register() {
        allow = true
        command("register", aliases = listOf("reg", "r")) {
            dynamic(commit = "password") {
                dynamic(commit = "confirm") {
                    execute<ProxyPlayer> { user, context, _ ->
                        if (FireflyXAPI.databaseAuth.registered(user.uniqueId)) {
                            user.sendLang("auth-register-already")
                            return@execute
                        }
                        val first = context.argument(-1)
                        val second = context.argument(0)
                        if (first == second) {
                            FireflyXAPI.databaseAuth.register(user, calculate(first))
                            user.sendLang("auth-register-success")
                        } else {
                            user.sendLang("auth-register-fail")
                        }
                    }
                }
            }
        }
        command("login", aliases = listOf("l")) {
            dynamic(commit = "password") {
                execute<ProxyPlayer> { user, context, _ ->
                    if (login.contains(user.uniqueId)) {
                        user.sendLang("auth-login-already")
                        return@execute
                    }
                    val password = calculate(context.argument(0))
                    val data = FireflyXAPI.databaseAuth.get(user.uniqueId)
                    if (password == data) {
                        user.sendLang("auth-login-success")
                    } else {
                        user.sendLang("auth-login-fail")
                    }
                }
            }
        }
        command("changepassword") {
            dynamic(commit = "old") {
                dynamic(commit = "new") {
                    dynamic(commit = "confirm") {
                        execute<ProxyPlayer> { user, context, _ ->
                            val old = context.argument(-2)
                            val new = context.argument(-1)
                            val confirm = context.argument(0)
                            if (FireflyXAPI.databaseAuth.get(user.uniqueId) != calculate(old)) {
                                user.sendLang("auth-change-old")
                                return@execute
                            }
                            if (new != confirm) {
                                user.sendLang("auth-change-confirm")
                                return@execute
                            }
                            FireflyXAPI.databaseAuth.register(user, calculate(new))
                            user.sendLang("auth-change-success")
                        }
                    }
                }
            }
        }
    }

    /**
     * 判断游戏名
     */
    @SubscribeEvent
    fun e(e: AsyncPlayerPreLoginEvent) {
        if (allow) {
            if (FireflyXAPI.databaseAuth.get(e.uniqueId) == null) {
                return
            }
            // 重复名
            if (FireflyXAPI.databaseAuth.contains(e.name.lowercase())) {
                val user = FireflyXAPI.databaseAuth.user(e.name.lowercase())!!
                val offline = plugin().server.getOfflinePlayer(user)
                // 触发大小写 Bug
                if (user != e.uniqueId) {
                    e.disallow(
                        AsyncPlayerPreLoginEvent.Result.KICK_OTHER,
                        console().asLangText("auth-name-kick", offline.name!!)
                    )
                    return
                }
                // 已经在线
                if (onlinePlayers().any { user == e.uniqueId }) {
                    e.disallow(
                        AsyncPlayerPreLoginEvent.Result.KICK_OTHER, console().asLangText("auth-online", offline.name!!)
                    )
                    return
                }
                // 只能由字母, 数字, 下划线构成用户名
                if (!e.name.matches(Regex("^[0-9a-zA-Z_]+$"))) {
                    e.disallow(
                        AsyncPlayerPreLoginEvent.Result.KICK_OTHER, console().asLangText("auth-regex")
                    )
                    return
                }
            }
        }
    }

    /**
     * 注册/登录
     */
    @SubscribeEvent
    fun e(e: PlayerJoinEvent) {
        if (allow) {
            submit(delay = FireflyXSettings.autoKickDelay) {
                e.player.kickPlayer(console().asLangText("auth-kick-delay"))
            }
            submit(period = FireflyXSettings.autoMsgCD) {
                if (FireflyXAPI.databaseAuth.registered(e.player.uniqueId)) {
                    e.player.sendLang("auth-login")
                } else {
                    e.player.sendLang("auth-register")
                }
            }
        }
    }

    /**
     * 注销登录
     */
    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        if (allow && login.contains(e.player.uniqueId)) {
            login.remove(e.player.uniqueId)
        }
    }

    /**
     * 拦截除注册登录以外的命令
     */
    @SubscribeEvent
    fun e(e: PlayerCommandPreprocessEvent) {
        if (allow && !login.contains(e.player.uniqueId) && !(e.message.startsWith("/register ") || e.message.startsWith("/reg ") || e.message.startsWith("/r ") || e.message.startsWith(
                "/login "
            ) || e.message.startsWith("/l "))
        ) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截聊天
     */
    @SubscribeEvent
    fun e(e: AsyncPlayerChatEvent) {
        if (allow && !login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截与方块交互
     */
    @SubscribeEvent
    fun e(e: PlayerInteractEvent) {
        if (allow && !login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截与实体交互
     */
    @SubscribeEvent
    fun e(e: PlayerInteractEntityEvent) {
        if (allow && !login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截打开容器
     */
    @SubscribeEvent
    fun e(e: InventoryOpenEvent) {
        if (allow && !login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截点击容器
     */
    @SubscribeEvent
    fun e(e: InventoryClickEvent) {
        if (allow && e.whoClicked is Player && !login.contains(e.whoClicked.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截攻击他人
     */
    @SubscribeEvent
    fun e(e: EntityDamageByEntityEvent) {
        if (allow && e.damager is Player && !login.contains(e.damager.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截受到攻击
     */
    @SubscribeEvent
    fun e(e: EntityDamageEvent) {
        if (allow && e.entity is Player && !login.contains(e.entity.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截传送
     */
    @SubscribeEvent
    fun e(e: PlayerTeleportEvent) {
        if (allow && !login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截丢弃
     */
    @SubscribeEvent
    fun e(e: PlayerDropItemEvent) {
        if (allow && !login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截拾取
     */
    @SubscribeEvent
    fun e(e: EntityPickupItemEvent) {
        if (allow && e.entity is Player && !login.contains(e.entity.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截移动
     */
    @SubscribeEvent
    fun e(e: PlayerMoveEvent) {
        if (allow && e.to != null && (e.from.distance(e.to!!) >= 0) && !login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截跳跃
     */
    @SubscribeEvent
    fun e(e: PlayerJumpEvent) {
        if (allow && !login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    private fun calculate(origin: String): String {
        val digest = MessageDigest.getInstance("MD5")
        val bytes = digest.digest(origin.toByteArray())
        val buffer = StringBuffer()
        for (byte in bytes) {
            val int = byte.toInt() and 0xff
            var hexString = Integer.toHexString(int)
            if (hexString.length < 2) {
                hexString = "0$hexString"
            }
            buffer.append(hexString)
        }
        return buffer.toString()
    }
}