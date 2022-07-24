package cn.micalhl.fireflyx.module.auth

import cn.micalhl.fireflyx.module.Module
import java.util.UUID

object Auth : Module {

    override var allow = false
    val login = arrayListOf<UUID>()

    override fun init() {
        super.init()
        AuthCommand.register()
    }
}