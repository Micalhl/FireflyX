package cn.micalhl.fireflyx.module.auth

import java.security.MessageDigest

fun calculate(origin: String): String {
    val digest = MessageDigest.getInstance("MD5")
    val bytes = digest.digest(origin.toByteArray())
    val buffer = StringBuffer()
    for (byte in bytes) {
        val int = byte.toInt() and 0xff
        var hexString = Integer.toHexString(int)
        if (hexString.length < 2) {
            hexString = "0$hexString"
        }
        buffer.append(hexString)
    }
    return buffer.toString()
}