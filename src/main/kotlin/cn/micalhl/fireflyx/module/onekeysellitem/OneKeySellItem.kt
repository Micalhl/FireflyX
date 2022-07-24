package cn.micalhl.fireflyx.module.onekeysellitem

import cn.micalhl.fireflyx.module.Module

/**
 * @link https://github.com/Micalhl/Satellite/blob/taboolib5/src/main/java/me/mical/satellite/module/OneKeySellItem.kt
 * @author Micalhl
 * @since 2021-06-06
 */
object OneKeySellItem : Module {

    override var allow = false

    override fun init() {
        super.init()
        OneKeySellItemCommand.register()
    }
}