package cn.micalhl.fireflyx

fun main() {
    println("Y_Mical".matches(Regex("^[0-9a-zA-Z_]+$")))
    println("Y$%$#%$#@%#$%$@#5_Mical".matches(Regex("^[0-9a-zA-Z_]+$")))
    println("Y_is脑瘫".matches(Regex("^[0-9a-zA-Z_]+$")))
    println("脑瘫".matches(Regex("^[0-9a-zA-Z_]+$")))
    if (!"詹姆斯蛋黄".matches(Regex("^[0-9a-zA-Z_]+$"))) {
        println("咸鸭蛋蛋黄")
    }
}