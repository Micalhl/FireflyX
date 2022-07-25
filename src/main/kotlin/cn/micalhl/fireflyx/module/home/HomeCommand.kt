package cn.micalhl.fireflyx.module.home

import cn.micalhl.fireflyx.api.FireflyXAPI
import cn.micalhl.fireflyx.module.back.Back
import cn.micalhl.fireflyx.module.home.ui.HomeMenu
import cn.micalhl.fireflyx.util.parseLocation
import cn.micalhl.fireflyx.util.parseString
import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.module.lang.sendLang

object HomeCommand {

    fun register() {
        command("home") {
            dynamic(optional = true, commit = "name") {
                suggestion<ProxyPlayer>(uncheck = true) { user, _ ->
                    FireflyXAPI.homeDatabase.get(user.uniqueId).map { it.name }
                }
                execute<ProxyPlayer> { user, context, _ ->
                    val name = context.argument(0)
                    if (!FireflyXAPI.homeDatabase.has(name, user.uniqueId)) {
                        user.sendLang("home-unknown", name)
                        return@execute
                    }
                    val home = FireflyXAPI.homeDatabase.get(name, user.uniqueId)!!
                    if (Back.allow) Back.dataMap[user.uniqueId] = user.location.parseString()
                    user.teleport(home.location.parseLocation())
                    user.sendLang("home-success", name)
                }
            }
            execute<ProxyPlayer> { user, _, _ ->
                if (FireflyXAPI.homeDatabase.get(user.uniqueId).isEmpty()) {
                    user.sendLang("home-no-home")
                    return@execute
                }
                if (FireflyXAPI.homeDatabase.get(user.uniqueId).size > 1) {
                    HomeMenu.open(user)
                } else {
                    val home = FireflyXAPI.homeDatabase.get(user.uniqueId)[0]
                    if (Back.allow) Back.dataMap[user.uniqueId] = user.location.parseString()
                    user.teleport(home.location.parseLocation())
                    user.sendLang("home-success", home.name)
                }
            }
        }
        command("sethome") {
            dynamic(optional = true, commit = "name") {
                execute<ProxyPlayer> { user, context, _ ->
                    val name = context.argument(0)
                    val location = user.location.parseString()
                    FireflyXAPI.homeDatabase.set(name, user.uniqueId, location)
                    user.sendLang("sethome-success", name)
                }
            }
            execute<ProxyPlayer> { user, _, _ ->
                val location = user.location.parseString()
                FireflyXAPI.homeDatabase.set("home", user.uniqueId, location)
                user.sendLang("sethome-success", "home")
            }
        }
        command("delhome") {
            dynamic(commit = "home") {
                suggestion<ProxyPlayer>(uncheck = true) { user, _ ->
                    FireflyXAPI.homeDatabase.get(user.uniqueId).map { it.name }
                }
                execute<ProxyPlayer> { user, context, _ ->
                    val name = context.argument(0)
                    if (!FireflyXAPI.homeDatabase.has(name, user.uniqueId)) {
                        user.sendLang("home-unknown", name)
                        return@execute
                    }
                    FireflyXAPI.homeDatabase.del(name, user.uniqueId)
                    user.sendLang("delhome-success", name)
                }
            }
        }
    }
}