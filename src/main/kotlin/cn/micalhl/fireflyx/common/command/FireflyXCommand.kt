package cn.micalhl.fireflyx.common.command

import cn.micalhl.fireflyx.common.config.Settings
import taboolib.common.platform.ProxyCommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper
import taboolib.module.lang.Language
import taboolib.module.lang.sendLang

@CommandHeader(name = "fireflyx")
object FireflyXCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

    @CommandBody
    val reload = subCommand {
        execute<ProxyCommandSender> { user, _, _ ->
            Settings.conf.reload()
            Language.reload()
            user.sendLang("plugin-reloaded")
        }
    }
}