/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.core.helper.model

import com.google.gson.annotations.SerializedName
import com.venomvendor.forza.core.annotation.Mandatory

data class TestData(
    @SerializedName("id") val id: Int,

    @Mandatory
    @SerializedName("name") val name: String
)
