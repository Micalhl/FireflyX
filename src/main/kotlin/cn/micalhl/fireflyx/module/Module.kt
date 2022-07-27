package cn.micalhl.fireflyx.module

interface Module {

    var allow: Boolean

    fun init() {
        allow = true
    }
}