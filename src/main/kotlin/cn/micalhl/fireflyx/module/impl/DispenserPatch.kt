package cn.micalhl.fireflyx.module.impl

import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.block.data.Directional
import org.bukkit.event.block.BlockDispenseEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.console
import taboolib.module.lang.sendLang
import taboolib.module.nms.MinecraftVersion
import taboolib.platform.util.onlinePlayers
import taboolib.platform.util.sendLang
import cn.micalhl.fireflyx.module.Module

object DispenserPatch : Module {

    var allow = false

    override fun register() {
        allow = MinecraftVersion.major < 5
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun e(e: BlockDispenseEvent) {
        val block = e.block
        if (block.type == Material.DISPENSER) {
            val face = (block.state.data as Directional).facing
            val y0FacingDown = block.y == 0 && face == BlockFace.DOWN
            val y0MaxFacingDown = block.y == block.world.maxHeight - 1 && face == BlockFace.UP
            if (y0FacingDown || y0MaxFacingDown) {
                if (e.item.type.name.lowercase().endsWith("shulker_box")) {
                    e.isCancelled = true
                    val loc = block.location
                    onlinePlayers.filter { it.location.distance(loc) <= 10.0 }.also {
                        console().sendLang("dispenser-patch-server",
                            loc.world!!.name,
                            loc.blockX,
                            loc.blockY,
                            loc.blockZ,
                            it.map { player -> player.name })
                    }.forEach { it.sendLang("dispenser-patch-player") }
                }
            }
        }
    }
}