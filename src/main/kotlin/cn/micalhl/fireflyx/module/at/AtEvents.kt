package cn.micalhl.fireflyx.module.at

import cn.micalhl.fireflyx.common.config.Settings
import cn.micalhl.fireflyx.util.plugin
import org.bukkit.event.player.AsyncPlayerChatEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.chat.colored
import taboolib.platform.util.asLangText
import taboolib.platform.util.onlinePlayers
import taboolib.platform.util.sendActionBar
import taboolib.platform.util.sendLang

object AtEvents {

    @SubscribeEvent
    fun e(e: AsyncPlayerChatEvent) {
        val message = e.message
        if (At.allow && e.message.contains("@")) {
            val index = message.indexOf("@")
            val left = message.substring(0, index)
            val right = message.substring(index)
            val whole = left.length + right.indexOf(" ")
            val name = message.substring(index + 1, whole)
            if (name == "all") {
                if (e.player.isOp) {
                    e.message = e.message.replace("@$name", "${Settings.atColor}@$name&r".colored())
                    onlinePlayers.forEach {
                        it.sendLang("at-receive", e.player.name)
                        it.sendActionBar(it.asLangText("at-receive", e.player.name))
                    }
                }
                return
            }
            if (e.player.name == name) {
                return
            }
            val target = plugin().server.getPlayerExact(name)
            if (target != null) {
                e.message = e.message.replace("@$name", "${Settings.atColor}@$name&r".colored())
                target.sendLang("at-receive", e.player.name)
                target.sendActionBar(target.asLangText("at-receive", e.player.name))
            }
        }
    }
}