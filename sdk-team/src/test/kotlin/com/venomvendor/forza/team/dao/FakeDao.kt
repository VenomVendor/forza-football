/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 24-Sep-2019.
 */

package com.venomvendor.forza.team.dao

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.venomvendor.forza.team.db.TeamDatabase
import org.mockito.Mockito

class FakeDao(private val teamDao: TeamDao?, private val teamDetailDao: TeamDetailDao?) :
    TeamDatabase() {
    override fun teamDao(): TeamDao {
        return teamDao!!
    }

    override fun teamDetailDao(): TeamDetailDao {
        return teamDetailDao!!
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        return Mockito.mock(SupportSQLiteOpenHelper::class.java)
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        return Mockito.mock(InvalidationTracker::class.java)
    }

    override fun clearAllTables() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
