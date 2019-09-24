package com.venomvendor.forza.team.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.venomvendor.forza.core.network.model.Status
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "teamDetail")
@Parcelize
data class TeamDetail(
    @field:SerializedName("badge_url")
    val badgeUrl: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("national")
    val national: Boolean,

    @field:SerializedName("description")
    val description: String,

    @PrimaryKey
    @ColumnInfo(name = "id")
    @field:SerializedName("id")
    val id: Int
) : Status(), Parcelable
