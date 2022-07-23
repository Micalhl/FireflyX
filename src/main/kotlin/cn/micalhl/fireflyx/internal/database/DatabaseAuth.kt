package cn.micalhl.fireflyx.internal.database

import taboolib.common.platform.ProxyPlayer
import taboolib.common.platform.function.getDataFolder
import taboolib.module.database.ColumnOptionSQLite
import taboolib.module.database.ColumnTypeSQLite
import taboolib.module.database.Table
import taboolib.module.database.getHost
import java.io.File
import java.util.UUID

class DatabaseAuth {

    private val host = File(getDataFolder(), "login.db").getHost()

    private val table = Table("fireflyx_login", host) {
        add("user") {
            type(ColumnTypeSQLite.TEXT) {
                options(ColumnOptionSQLite.PRIMARY_KEY)
            }
        }
        add("name") {
            type(ColumnTypeSQLite.TEXT)
        }
        add("password") {
            type(ColumnTypeSQLite.TEXT)
        }
    }

    private val dataSource = host.createDataSource()

    init {
        table.workspace(dataSource) { createTable(true) }.run()
    }

    fun register(user: ProxyPlayer, password: String) {
        table.insert(dataSource, "user", "name", "password") {
            value(user.uniqueId, user.name.lowercase(), password)
        }
    }

    fun registered(user: UUID): Boolean {
        return table.find(dataSource) {
            where { "user" eq user }
        }
    }

    fun contains(name: String): Boolean {
        return table.find(dataSource) {
            where { "name" eq name }
        }
    }

    fun user(name: String): UUID? {
        return table.select(dataSource) {
            rows("user")
            where { "name" eq name }
        }.firstOrNull { UUID.fromString(getString("user")) }
    }

    fun get(user: UUID): String? {
        return table.select(dataSource) {
            rows("password")
            where { "user" eq user }
        }.firstOrNull { getString("password") }
    }
}