package cn.micalhl.fireflyx.module.online

import cn.micalhl.fireflyx.module.Module

object Online : Module {

    override var allow = false

    override fun init() {
        super.init()
        OnlineCommand.register()
    }
}