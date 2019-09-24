/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.app.core

import android.app.Application
import com.venomvendor.forza.core.di.coreModule
import com.venomvendor.forza.team.di.teamModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application class for core functionality.
 */
open class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // start Koin
        startKoin {
            // Declare used Android context
            androidContext(this@CustomApplication)
            // Declare modules
            modules(
                listOf(
                    coreModule, teamModule
                )
            )
        }
    }
}
