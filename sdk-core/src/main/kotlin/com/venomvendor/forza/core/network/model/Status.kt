/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.core.network.model

import androidx.room.Ignore
import com.google.gson.annotations.SerializedName

open class Status(
    @Ignore
    @SerializedName("error")
    val error: String? = null,

    @Ignore
    @SerializedName("message")
    val message: String? = null,

    @Ignore
    @SerializedName("statusCode")
    val statusCode: Int? = null
) {
    val isSuccess: Boolean
        get() = error == null
}
