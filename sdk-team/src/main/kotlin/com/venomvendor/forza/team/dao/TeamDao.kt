/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 24-Sep-2019.
 */

package com.venomvendor.forza.team.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.venomvendor.forza.team.model.Team
import io.reactivex.Completable

@Dao
interface TeamDao {
    @Query("SELECT * FROM team")
    fun getAll(): List<Team>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg teams: Team): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(teams: List<Team>): Completable

    @Delete
    fun delete(team: Team): Completable

    @Query("DELETE FROM team")
    fun deleteAll()
}
