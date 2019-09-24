/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.helper.di

import com.venomvendor.forza.core.helper.CoreConstant.Companion.QUALIFIER_BASE_URL
import okhttp3.mockwebserver.MockWebServer
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appTestModule = module(override = true) {

    single {
        val server = MockWebServer()
        server.start()
        val url = server.url("/").toString()
        println("Server is running at $url")
        server
    }

    /**
     * Provides Instance of networking client's base url
     */
    single(named(QUALIFIER_BASE_URL), false, true) {
        val server = get<MockWebServer>()
        server.url("/").toString()
    }
}
