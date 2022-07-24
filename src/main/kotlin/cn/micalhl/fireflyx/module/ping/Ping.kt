package cn.micalhl.fireflyx.module.ping

import cn.micalhl.fireflyx.module.Module

object Ping : Module {

    override var allow = false

    override fun init() {
        super.init()
        PingCommand.register()
    }
}