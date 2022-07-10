package work.crash.micalhl.fireflyx.module.database

import taboolib.common.platform.function.getDataFolder
import taboolib.module.database.ColumnOptionSQLite
import taboolib.module.database.ColumnTypeSQLite
import taboolib.module.database.Table
import taboolib.module.database.getHost
import work.crash.micalhl.fireflyx.module.config.Settings
import java.io.File
import java.util.UUID

class DatabaseEconomy {

    private val host = File(getDataFolder(), "economy.db").getHost()

    private val table = Table("fireflyx_economy", host) {
        add("user") {
            type(ColumnTypeSQLite.TEXT) {
                options(ColumnOptionSQLite.PRIMARY_KEY)
            }
        }
        add("economy") {
            type(ColumnTypeSQLite.REAL)
        }
    }

    private val dataSource = host.createDataSource()

    init {
        table.workspace(dataSource) { createTable(true) }.run()
    }

    fun has(user: UUID): Boolean {
        return table.find(dataSource) {
            where { "user" eq user }
        }
    }

    fun create(user: UUID, economy: Double = Settings.firstJoinMoney) {
        table.insert(dataSource, "user", "economy") {
            value(user, economy)
        }
    }

    fun get(user: UUID): Double {
        return table.select(dataSource) {
            rows("economy")
            where { "user" eq user }
        }.firstOrNull { getDouble("economy") } ?: 0.0
    }

    fun add(user: UUID, economy: Double) {
        val money = get(user)
        table.update(dataSource) {
            set("economy", money + economy)
            where { "user" eq user }
        }
    }

    fun take(user: UUID, economy: Double) {
        val money = get(user)
        table.update(dataSource) {
            set("economy", money - economy)
            where { "user" eq user }
        }
    }

    fun set(user: UUID, economy: Double) {
        table.update(dataSource) {
            set("economy", economy)
            where { "user" eq user }
        }
    }

}