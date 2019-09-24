package com.venomvendor.forza.team.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.venomvendor.forza.core.annotation.Mandatory
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "team")
@Mandatory
@Parcelize
data class Team(
    @ColumnInfo(name = "gender")
    @field:SerializedName("gender")
    val gender: String,

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    val name: String,

    @ColumnInfo(name = "national")
    @field:SerializedName("national")
    val national: Boolean,

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    val id: Int
) : Parcelable
