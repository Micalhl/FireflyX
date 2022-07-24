package cn.micalhl.fireflyx.module.tps

import cn.micalhl.fireflyx.module.Module

object Tps : Module {

    override var allow = false

    override fun init() {
        super.init()
        TpsCommand.register()
    }
}