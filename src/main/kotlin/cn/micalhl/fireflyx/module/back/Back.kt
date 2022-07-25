package cn.micalhl.fireflyx.module.back

import cn.micalhl.fireflyx.module.Module
import java.util.UUID

object Back : Module {

    override var allow = false
    val dataMap = hashMapOf<UUID, String>()

    override fun init() {
        super.init()
        BackCommand.register()
    }
}