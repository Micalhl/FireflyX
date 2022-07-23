package cn.micalhl.fireflyx.module.impl

import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.command.command
import taboolib.module.lang.sendLang
import cn.micalhl.fireflyx.api.FireflyXAPI
import cn.micalhl.fireflyx.internal.ui.HomeMenu
import cn.micalhl.fireflyx.module.Module
import cn.micalhl.fireflyx.util.parseLocation
import cn.micalhl.fireflyx.util.parseString

object Home : Module {

    override fun register() {
        command("home") {
            dynamic(optional = true, commit = "name") {
                suggestion<ProxyPlayer>(uncheck = true) { user, _ ->
                    FireflyXAPI.databaseHome.get(user.uniqueId).map { it.name }
                }
                execute<ProxyPlayer> { user, context, _ ->
                    val name = context.argument(0)
                    if (!cn.micalhl.fireflyx.api.FireflyXAPI.databaseHome.has(name, user.uniqueId)) {
                        user.sendLang("home-unknown", name)
                        return@execute
                    }
                    val home = FireflyXAPI.databaseHome.get(name, user.uniqueId)
                    user.teleport(home!!.location.parseLocation())
                    user.sendLang("home-success", name)
                }
            }
            execute<ProxyPlayer> { user, _, _ ->
                if (cn.micalhl.fireflyx.api.FireflyXAPI.databaseHome.get(user.uniqueId).isEmpty()) {
                    user.sendLang("home-no-home")
                    return@execute
                }
                if (cn.micalhl.fireflyx.api.FireflyXAPI.databaseHome.get(user.uniqueId).size > 1) {
                    HomeMenu.open(user)
                } else {
                    val home = FireflyXAPI.databaseHome.get(user.uniqueId)[0]
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
                    FireflyXAPI.databaseHome.set(name, user.uniqueId, location)
                    user.sendLang("sethome-success", name)
                }
            }
            execute<ProxyPlayer> { user, _, _ ->
                val location = user.location.parseString()
                FireflyXAPI.databaseHome.set("home", user.uniqueId, location)
                user.sendLang("sethome-success", "home")
            }
        }
        command("delhome") {
            dynamic(commit = "home") {
                suggestion<ProxyPlayer>(uncheck = true) { user, _ ->
                    FireflyXAPI.databaseHome.get(user.uniqueId).map { it.name }
                }
                execute<ProxyPlayer> { user, context, _ ->
                    val name = context.argument(0)
                    if (!cn.micalhl.fireflyx.api.FireflyXAPI.databaseHome.has(name, user.uniqueId)) {
                        user.sendLang("home-unknown", name)
                        return@execute
                    }
                    FireflyXAPI.databaseHome.del(name, user.uniqueId)
                    user.sendLang("delhome-success", name)
                }
            }
        }
    }
}