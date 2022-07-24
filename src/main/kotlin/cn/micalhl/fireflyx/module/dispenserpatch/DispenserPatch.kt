package cn.micalhl.fireflyx.module.dispenserpatch

import cn.micalhl.fireflyx.module.Module
import taboolib.module.nms.MinecraftVersion

object DispenserPatch : Module {

    override var allow = false

    override fun init() {
        super.init()
        // 这个 Bug 只有 1.13 以下的服务端触发
        allow = allow && MinecraftVersion.major < 5
    }
}