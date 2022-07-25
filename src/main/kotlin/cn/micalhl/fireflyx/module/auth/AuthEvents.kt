package cn.micalhl.fireflyx.module.auth

import cn.micalhl.fireflyx.api.FireflyXAPI
import cn.micalhl.fireflyx.common.config.Settings
import cn.micalhl.fireflyx.util.plugin
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.*
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.console
import taboolib.common.platform.function.onlinePlayers
import taboolib.common.platform.function.submit
import taboolib.module.lang.asLangText
import taboolib.platform.event.PlayerJumpEvent
import taboolib.platform.util.sendLang

object AuthEvents {

    /**
     * 判断游戏名
     */
    @SubscribeEvent
    fun e(e: AsyncPlayerPreLoginEvent) {
        if (Auth.allow) {
            if (FireflyXAPI.authDatabase.get(e.uniqueId) == null) {
                return
            }
            // 重复名
            if (FireflyXAPI.authDatabase.contains(e.name.lowercase())) {
                val user = FireflyXAPI.authDatabase.user(e.name.lowercase())!!
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
                if (onlinePlayers().any { it.name == e.name }) {
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
        if (Auth.allow) {
            submit(delay = Settings.autoKickDelay) {
                if (!Auth.login.contains(e.player.uniqueId)) {
                    e.player.kickPlayer(console().asLangText("auth-kick-delay"))
                }
            }
            submit(period = Settings.autoMsgCD) {
                if (!Auth.login.contains(e.player.uniqueId)) {
                    if (FireflyXAPI.authDatabase.registered(e.player.uniqueId)) {
                        e.player.sendLang("auth-login")
                    } else {
                        e.player.sendLang("auth-register")
                    }
                }
            }
        }
    }

    /**
     * 拦截方块破坏
     */
    @SubscribeEvent
    fun e(e: BlockBreakEvent) {
        if (Auth.allow && !Auth.login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截方块放置
     */
    @SubscribeEvent
    fun e(e: BlockPlaceEvent) {
        if (Auth.allow && !Auth.login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 注销登录
     */
    @SubscribeEvent
    fun e(e: PlayerQuitEvent) {
        if (Auth.allow && Auth.login.contains(e.player.uniqueId)) {
            Auth.login.remove(e.player.uniqueId)
        }
    }

    /**
     * 拦截除注册登录以外的命令
     */
    @SubscribeEvent
    fun e(e: PlayerCommandPreprocessEvent) {
        if (Auth.allow && !Auth.login.contains(e.player.uniqueId) && !(e.message.startsWith("/register ") || e.message.startsWith(
                "/reg "
            ) || e.message.startsWith("/r ") || e.message.startsWith(
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
        if (Auth.allow && !Auth.login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截与方块交互
     */
    @SubscribeEvent
    fun e(e: PlayerInteractEvent) {
        if (Auth.allow && !Auth.login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截与实体交互
     */
    @SubscribeEvent
    fun e(e: PlayerInteractEntityEvent) {
        if (Auth.allow && !Auth.login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截打开容器
     */
    @SubscribeEvent
    fun e(e: InventoryOpenEvent) {
        if (Auth.allow && !Auth.login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截点击容器
     */
    @SubscribeEvent
    fun e(e: InventoryClickEvent) {
        if (Auth.allow && e.whoClicked is Player && !Auth.login.contains(e.whoClicked.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截攻击他人
     */
    @SubscribeEvent
    fun e(e: EntityDamageByEntityEvent) {
        if (Auth.allow && e.damager is Player && !Auth.login.contains(e.damager.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截受到攻击
     */
    @SubscribeEvent
    fun e(e: EntityDamageEvent) {
        if (Auth.allow && e.entity is Player && !Auth.login.contains(e.entity.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截传送
     */
    @SubscribeEvent
    fun e(e: PlayerTeleportEvent) {
        if (Auth.allow && !Auth.login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截丢弃
     */
    @SubscribeEvent
    fun e(e: PlayerDropItemEvent) {
        if (Auth.allow && !Auth.login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截拾取
     */
    @SubscribeEvent
    fun e(e: EntityPickupItemEvent) {
        if (Auth.allow && e.entity is Player && !Auth.login.contains(e.entity.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截移动
     */
    @SubscribeEvent
    fun e(e: PlayerMoveEvent) {
        if (Auth.allow && e.to != null && (e.from.distance(e.to!!) >= 0) && !Auth.login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }

    /**
     * 拦截跳跃
     */
    @SubscribeEvent
    fun e(e: PlayerJumpEvent) {
        if (Auth.allow && !Auth.login.contains(e.player.uniqueId)) {
            e.isCancelled = true
        }
    }
}