package cn.micalhl.fireflyx.api

import cn.micalhl.fireflyx.module.money.database.EconomyDatabase
import cn.micalhl.fireflyx.module.home.database.HomeDatabase
import cn.micalhl.fireflyx.module.auth.database.AuthDatabase

object FireflyXAPI {

    val economyDatabase = EconomyDatabase()
    val homeDatabase = HomeDatabase()
    val authDatabase = AuthDatabase()
}