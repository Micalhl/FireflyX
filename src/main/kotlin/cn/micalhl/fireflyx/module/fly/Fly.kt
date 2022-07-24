package cn.micalhl.fireflyx.module.fly

import cn.micalhl.fireflyx.module.Module

object Fly : Module {

    override var allow = false

    override fun init() {
        super.init()
        FlyCommand.register()
    }
}