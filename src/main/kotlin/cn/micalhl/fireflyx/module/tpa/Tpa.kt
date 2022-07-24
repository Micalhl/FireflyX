package cn.micalhl.fireflyx.module.tpa

import cn.micalhl.fireflyx.module.Module
import java.util.*

object Tpa : Module {

    val tpaData = hashMapOf<UUID, UUID>()
    val tpahere = hashMapOf<UUID, Boolean>()

    override var allow = false

    override fun init() {
        super.init()
        TpaCommand.register()
    }
}