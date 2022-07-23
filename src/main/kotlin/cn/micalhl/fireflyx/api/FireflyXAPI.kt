package cn.micalhl.fireflyx.api

import cn.micalhl.fireflyx.internal.database.DatabaseEconomy
import cn.micalhl.fireflyx.internal.database.DatabaseHome
import cn.micalhl.fireflyx.internal.database.DatabaseAuth

object FireflyXAPI {

    val databaseEconomy = DatabaseEconomy()
    val databaseHome = DatabaseHome()
    val databaseAuth = DatabaseAuth()
}