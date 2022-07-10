package work.crash.micalhl.fireflyx.module.database

import taboolib.common.platform.function.getDataFolder
import taboolib.module.database.ColumnOptionSQLite
import taboolib.module.database.ColumnTypeSQLite
import taboolib.module.database.Table
import taboolib.module.database.getHost
import java.io.File
import java.util.UUID

class DatabaseHome {

    private val host = File(getDataFolder(), "home.db").getHost()

    private val table = Table("fireflyx_home", host) {
        add("home") {
            type(ColumnTypeSQLite.TEXT) {
                options(ColumnOptionSQLite.PRIMARY_KEY)
            }
        }
        add("name") {
            type(ColumnTypeSQLite.TEXT)
        }
        add("user") {
            type(ColumnTypeSQLite.TEXT)
        }
        add("location") {
            type(ColumnTypeSQLite.TEXT)
        }
    }

    private val dataSource = host.createDataSource()

    init {
        table.workspace(dataSource) { createTable(true) }.run()
    }

    fun get(name: String, user: UUID): Home? {
        return table.select(dataSource) {
            rows("home", "location")
            where {
                "name" eq name
                "user" eq user
            }
        }.firstOrNull { Home(UUID.fromString(getString("home")), name, user, getString("location")) }
    }

    fun has(name: String, user: UUID): Boolean {
        return table.find(dataSource) {
            where {
                "name" eq name
                "user" eq user
            }
        }
    }

    fun set(name: String, user: UUID, location: String) {
        if (has(name, user)) {
            table.update(dataSource) {
                set("name", name)
                set("location", location)
                where {
                    "name" eq name
                    "user" eq user
                }
            }
        } else {
            table.insert(dataSource, "home", "name", "user", "location") {
                value(UUID.randomUUID(), name, user, location)
            }
        }
    }

    fun del(name: String, user: UUID) {
        table.delete(dataSource) {
            where {
                "name" eq name
                "user" eq user
            }
        }
    }

    data class Home(val home: UUID, val name: String, val user: UUID, val location: String)

}