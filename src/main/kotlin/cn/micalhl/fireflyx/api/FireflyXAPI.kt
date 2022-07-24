package cn.micalhl.fireflyx.api

import cn.micalhl.fireflyx.common.database.DatabaseEconomy
import cn.micalhl.fireflyx.common.database.DatabaseHome
import cn.micalhl.fireflyx.common.database.DatabaseAuth

object FireflyXAPI {

    val databaseEconomy = DatabaseEconomy()
    val databaseHome = DatabaseHome()
    val databaseAuth = DatabaseAuth()
}