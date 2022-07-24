package cn.micalhl.fireflyx

import cn.micalhl.fireflyx.common.filter.Log4JFilter
import org.apache.logging.log4j.LogManager
import kotlin.math.log

object Log4JFilterTest {

    @JvmStatic
    fun main(args: Array<String>) {
        val logger = LogManager.getLogger(this.javaClass)
        logger.info("123")
    }
}


fun main0() {

    //println(Log4JFilterTest.logger)
    //val logger: org.apache.logging.log4j.core.Logger = LogManager.getLogger(Log4JFilterTest.javaClass) as org.apache.logging.log4j.core.Logger
    //logger.addFilter(Log4JFilter())
   // println("123456")
    //logger.info("123")
    //logger.info("Y_Mical issued server command: /login 123456")
    //logger.info("Y_Mical issued server command: /tpa CN_Eicy")
    //logger.info("Please use /login to login your account.")
}