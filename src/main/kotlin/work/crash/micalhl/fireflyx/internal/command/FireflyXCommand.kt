package work.crash.micalhl.fireflyx.internal.command

import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper

@CommandHeader(name = "fireflyx")
object FireflyXCommand {

    @CommandBody
    val main = mainCommand {
        createHelper()
    }

}