package cn.micalhl.fireflyx.util

inline fun <reified T> cast(obj: Any): T = obj as T

fun String.isDouble(): Boolean {
    return try {
        toDouble()
        true
    } catch (e: Throwable) {
        false
    }
}