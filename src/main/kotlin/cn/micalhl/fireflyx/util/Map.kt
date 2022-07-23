package cn.micalhl.fireflyx.util

inline fun <reified T, R> Map<T, R>.getKeys(value: Any): List<T> {
    val result = arrayListOf<T>()
    for (entry in entries) {
        if (entry.value == value) {
            result.add(entry.key)
        }
    }
    return result
}