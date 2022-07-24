package cn.micalhl.fireflyx.module.home

import cn.micalhl.fireflyx.module.Module

object Home : Module {

    override var allow = false

    override fun init() {
        super.init()
        HomeCommand.register()
    }
}