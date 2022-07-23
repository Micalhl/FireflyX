package cn.micalhl.fireflyx.api

import cn.micalhl.fireflyx.internal.database.DatabaseEconomy
import cn.micalhl.fireflyx.internal.database.DatabaseHome

object FireflyXAPI {

    val databaseEconomy = DatabaseEconomy()
    val databaseHome = DatabaseHome()
}