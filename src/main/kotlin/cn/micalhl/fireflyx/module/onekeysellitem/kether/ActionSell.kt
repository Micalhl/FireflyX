package cn.micalhl.fireflyx.module.onekeysellitem.kether

import cn.micalhl.fireflyx.module.onekeysellitem.oneKeySellItem
import org.bukkit.Material
import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptPlayer
import taboolib.module.kether.*
import java.util.concurrent.CompletableFuture

class ActionSell(private val material: Material, private val price: Double) : ScriptAction<Double>() {

    override fun run(frame: ScriptFrame): CompletableFuture<Double> {
        val player = frame.script().sender?.castSafely<Player>() ?: error("player is offline or unknown")
        return CompletableFuture.completedFuture(adaptPlayer(player).oneKeySellItem(material.name, price).toDouble())
    }

    companion object {

        @KetherParser(["sell"], shared = true)
        fun parser() = scriptParser {
            it.switch {
                case("item") {
                    val material = it.run {
                        it.expect("is")
                        Material.getMaterial(it.nextToken())
                    } ?: Material.AIR
                    val amount = it.run {
                        it.mark()
                        it.expect("price")
                        it.nextDouble()
                    }
                    ActionSell(material, amount)
                }
            }
        }
    }
}