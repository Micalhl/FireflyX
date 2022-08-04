package cn.micalhl.fireflyx.module.stops

import cn.micalhl.fireflyx.module.Module

object Stops : Module {

    override var allow = false

    override fun init() {
        super.init()
        StopsCommand.register()
    }
}