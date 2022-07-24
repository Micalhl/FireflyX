package cn.micalhl.fireflyx.module.teleportrandom

import cn.micalhl.fireflyx.module.Module

object TeleportRandom : Module {

    override var allow = false

    override fun init() {
        super.init()
        TeleportRandomCommand.register()
    }
}