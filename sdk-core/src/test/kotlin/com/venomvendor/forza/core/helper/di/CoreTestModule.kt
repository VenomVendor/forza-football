/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.core.helper.di

import com.venomvendor.forza.core.helper.CoreConstant.Companion.QUALIFIER_BASE_URL
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val BASE_URL = "http://localhost:8080/"

val testModule = module(override = true) {

    /**
     * Provides Instance of networking client's base url
     */
    single(named(QUALIFIER_BASE_URL), false, true) {
        BASE_URL
    }
}
