package cn.micalhl.fireflyx.internal.command

import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.expansion.createHelper

@CommandHeader(name = "fireflyx")
object FireflyXCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }
}