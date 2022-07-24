package cn.micalhl.fireflyx.module.spawn

import cn.micalhl.fireflyx.module.Module

object Spawn : Module {

    override var allow = false

    override fun init() {
        super.init()
        SpawnCommand.register()
    }
}