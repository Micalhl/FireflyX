package work.crash.micalhl.fireflyx.module.command

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.common.platform.function.getProxyPlayer
import taboolib.common.platform.function.onlinePlayers
import taboolib.common.platform.function.submit
import taboolib.module.lang.sendLang
import work.crash.micalhl.fireflyx.module.config.Settings
import work.crash.micalhl.fireflyx.util.getKeys
import java.util.UUID

object TpCommand {

    private val tpaData = hashMapOf<UUID, UUID>()
    private val tpahere = hashMapOf<UUID, Boolean>()

    @Awake(LifeCycle.ACTIVE)
    fun register() {
        command("tpa") {
            dynamic(commit = "player") {
                suggestion<ProxyPlayer>(uncheck = true) { _, _ ->
                    onlinePlayers().map { it.name }
                }
                execute<ProxyPlayer> { user, context, _ ->
                    val name = context.argument(0)
                    val target = getProxyPlayer(name)
                    if (target == null) {
                        user.sendLang("common-player-null")
                        return@execute
                    }
                    if (target.name == user.name) {
                        user.sendLang("tp-self")
                        return@execute
                    }
                    tpaData[target.uniqueId] = user.uniqueId
                    tpahere[target.uniqueId] = false
                    user.sendLang("tp-send", name)
                    target.sendLang("tpa-receive", user.name, Settings.teleportTime)
                    submit(delay = Settings.teleportTime * 20L) {
                        tpaData.remove(target.uniqueId)
                        tpahere.remove(target.uniqueId)
                    }
                }
            }
        }
        command("tpahere") {
            dynamic(commit = "player") {
                suggestion<ProxyPlayer>(uncheck = true) { _, _ ->
                    onlinePlayers().map { it.name }
                }
                execute<ProxyPlayer> { user, context, _ ->
                    val name = context.argument(0)
                    val target = getProxyPlayer(name)
                    if (target == null) {
                        user.sendLang("common-player-null")
                        return@execute
                    }
                    if (target.name == user.name) {
                        user.sendLang("tp-self")
                        return@execute
                    }
                    tpaData[target.uniqueId] = user.uniqueId
                    tpahere[target.uniqueId] = true
                    user.sendLang("tp-send", name)
                    target.sendLang("tpahere-receive", user.name, Settings.teleportTime)
                    submit(delay = Settings.teleportTime * 20L) {
                        tpaData.remove(target.uniqueId)
                        tpahere.remove(target.uniqueId)
                    }
                }
            }
        }
        command("tpacancel") {
            dynamic(commit = "player") {
                suggestion<ProxyPlayer>(uncheck = true) { user, _ ->
                    tpaData.getKeys(user.uniqueId).filter { getProxyPlayer(it) != null }
                        .map { getProxyPlayer(it)!!.name }
                }
                execute<ProxyPlayer> { user, context, _ ->
                    val name = context.argument(0)
                    val player = getProxyPlayer(name)
                    if (player == null) {
                        user.sendLang("common-player-null")
                        return@execute
                    }
                    tpaData.remove(player.uniqueId)
                    tpahere.remove(player.uniqueId)
                    user.sendLang("tpacancel-success", name)
                }
            }
        }
        command("tpdeny") {
            execute<ProxyPlayer> { user, _, _ ->
                if (!tpaData.containsKey(user.uniqueId)) {
                    user.sendLang("tp-no-request")
                    return@execute
                }
                val source = tpaData[user.uniqueId]!!
                val player = getProxyPlayer(source)
                if (player == null) {
                    user.sendLang("common-player-null")
                    return@execute
                }
                tpaData.remove(user.uniqueId)
                tpahere.remove(user.uniqueId)
                player.sendLang("tp-deny")
                user.sendLang("tp-deny-self")
            }
        }
        command("tpaccept") {
            execute<ProxyPlayer> { user, _, _ ->
                if (!tpaData.containsKey(user.uniqueId)) {
                    user.sendLang("tp-no-request")
                    return@execute
                }
                val source = tpaData[user.uniqueId]!!
                val player = getProxyPlayer(source)
                if (player == null) {
                    user.sendLang("common-player-null")
                    return@execute
                }
                if (tpahere[user.uniqueId]!!) {
                    user.sendLang("tpahere-accpet-self", Settings.teleportCD)
                    player.sendLang("tpahere-accept")
                    submit(delay = Settings.teleportCD * 20L) {
                        user.teleport(player.location)
                    }
                } else {
                    user.sendLang("tpa-accpet-self")
                    player.sendLang("tpa-accpet", Settings.teleportCD)
                    submit(delay = Settings.teleportCD * 20L) {
                        player.teleport(user.location)
                    }
                }
                tpaData.remove(source)
                tpahere.remove(user.uniqueId)
            }
        }
    }

}