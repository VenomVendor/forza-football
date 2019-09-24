/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 24-Sep-2019.
 */

package com.venomvendor.forza.team.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.venomvendor.forza.team.dao.TeamDao
import com.venomvendor.forza.team.dao.TeamDetailDao
import com.venomvendor.forza.team.model.Team
import com.venomvendor.forza.team.model.TeamDetail

/**
 * The Room database that contains the Users table
 */
@Database(entities = [Team::class, TeamDetail::class], version = 1)
abstract class TeamDatabase : RoomDatabase() {

    abstract fun teamDao(): TeamDao

    abstract fun teamDetailDao(): TeamDetailDao

    companion object {

        @Volatile
        private var INSTANCE: TeamDatabase? = null

        fun getInstance(context: Context): TeamDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TeamDatabase::class.java, "team.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}
