package cn.micalhl.fireflyx.module.tpa

import cn.micalhl.fireflyx.common.config.Settings
import cn.micalhl.fireflyx.module.back.Back
import cn.micalhl.fireflyx.util.getKeys
import cn.micalhl.fireflyx.util.parseString
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.common.platform.function.getProxyPlayer
import taboolib.common.platform.function.onlinePlayers
import taboolib.common.platform.function.submit
import taboolib.module.lang.sendLang

object TpaCommand {

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
                    Tpa.tpaData[target.uniqueId] = user.uniqueId
                    Tpa.tpahere[target.uniqueId] = false
                    user.sendLang("tp-send", name)
                    target.sendLang("tpa-receive", user.name, Settings.teleportTime)
                    submit(delay = Settings.teleportTime * 20L) {
                        Tpa.tpaData.remove(target.uniqueId)
                        Tpa.tpahere.remove(target.uniqueId)
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
                    Tpa.tpaData[target.uniqueId] = user.uniqueId
                    Tpa.tpahere[target.uniqueId] = true
                    user.sendLang("tp-send", name)
                    target.sendLang("tpahere-receive", user.name, Settings.teleportTime)
                    submit(delay = Settings.teleportTime * 20L) {
                        Tpa.tpaData.remove(target.uniqueId)
                        Tpa.tpahere.remove(target.uniqueId)
                    }
                }
            }
        }
        command("tpacancel") {
            dynamic(commit = "player") {
                suggestion<ProxyPlayer>(uncheck = true) { user, _ ->
                    Tpa.tpaData.getKeys(user.uniqueId).filter { getProxyPlayer(it) != null }
                        .map { getProxyPlayer(it)!!.name }
                }
                execute<ProxyPlayer> { user, context, _ ->
                    val name = context.argument(0)
                    val player = getProxyPlayer(name)
                    if (player == null) {
                        user.sendLang("common-player-null")
                        return@execute
                    }
                    Tpa.tpaData.remove(player.uniqueId)
                    Tpa.tpahere.remove(player.uniqueId)
                    user.sendLang("tpacancel-success", name)
                }
            }
        }
        command("tpdeny") {
            execute<ProxyPlayer> { user, _, _ ->
                if (!Tpa.tpaData.containsKey(user.uniqueId)) {
                    user.sendLang("tp-no-request")
                    return@execute
                }
                val source = Tpa.tpaData[user.uniqueId]!!
                val player = getProxyPlayer(source)
                if (player == null) {
                    user.sendLang("common-player-null")
                    return@execute
                }
                Tpa.tpaData.remove(user.uniqueId)
                Tpa.tpahere.remove(user.uniqueId)
                player.sendLang("tp-deny")
                user.sendLang("tp-deny-self")
            }
        }
        command("tpaccept") {
            execute<ProxyPlayer> { user, _, _ ->
                if (!Tpa.tpaData.containsKey(user.uniqueId)) {
                    user.sendLang("tp-no-request")
                    return@execute
                }
                val source = Tpa.tpaData[user.uniqueId]!!
                val player = getProxyPlayer(source)
                if (player == null) {
                    user.sendLang("common-player-null")
                    return@execute
                }
                if (Tpa.tpahere[user.uniqueId]!!) {
                    user.sendLang("tpahere-accept-self", Settings.teleportCD)
                    player.sendLang("tpahere-accept")
                    if (Back.allow) Back.dataMap[user.uniqueId] = user.location.parseString()
                    submit(delay = Settings.teleportCD * 20L) {
                        user.teleport(player.location)
                    }
                } else {
                    user.sendLang("tpa-accept-self")
                    player.sendLang("tpa-accept", Settings.teleportCD)
                    if (Back.allow) Back.dataMap[player.uniqueId] = player.location.parseString()
                    submit(delay = Settings.teleportCD * 20L) {
                        player.teleport(user.location)
                    }
                }
                Tpa.tpaData.remove(source)
                Tpa.tpahere.remove(user.uniqueId)
            }
        }
    }
}