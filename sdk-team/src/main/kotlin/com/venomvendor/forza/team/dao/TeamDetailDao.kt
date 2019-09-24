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
import com.venomvendor.forza.team.model.TeamDetail
import io.reactivex.Completable

@Dao
interface TeamDetailDao {
    @Query("SELECT * FROM teamDetail")
    fun getAll(): List<TeamDetail>

    @Query("SELECT * FROM teamDetail WHERE id = :id")
    fun getById(id: Int): TeamDetail

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg teamDetail: TeamDetail): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(teamDetails: List<TeamDetail>): Completable

    @Delete
    fun delete(team: TeamDetail): Completable

    @Query("DELETE FROM teamDetail")
    fun deleteAll()
}
