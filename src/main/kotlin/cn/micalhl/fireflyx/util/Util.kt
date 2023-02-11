package cn.micalhl.fireflyx.util

fun String.isDouble(): Boolean {
    return try {
        toDouble()
        true
    } catch (e: Throwable) {
        false
    }
}