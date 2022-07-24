package cn.micalhl.fireflyx.common.filter

import cn.micalhl.fireflyx.module.impl.Auth
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.Marker
import org.apache.logging.log4j.core.Filter
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.Logger
import org.apache.logging.log4j.core.filter.AbstractFilter
import org.apache.logging.log4j.message.Message

class Log4JFilter : AbstractFilter() {

    private val serialVersionUID = 5039752678304100452L

    private fun validate(message: Message?): Filter.Result {
        return if (message == null) Filter.Result.NEUTRAL else validate(message.formattedMessage)
    }

    private fun validate(message: String): Filter.Result {
        return if (Auth.isAuthCommand(message.lowercase())) Filter.Result.DENY else Filter.Result.NEUTRAL
    }

    override fun filter(event: LogEvent?): Filter.Result {
        return if (event == null) Filter.Result.NEUTRAL else validate(event.message)
    }

    override fun filter(logger: Logger?, level: Level?, marker: Marker?, msg: Message?, t: Throwable?): Filter.Result {
        return validate(msg)
    }

    override fun filter(
        logger: Logger?,
        level: Level?,
        marker: Marker?,
        msg: String?,
        vararg params: Any?
    ): Filter.Result {
        return validate(msg ?: "")
    }

    override fun filter(logger: Logger?, level: Level?, marker: Marker?, msg: Any?, t: Throwable?): Filter.Result {
        return validate(msg?.toString() ?: "")
    }
}