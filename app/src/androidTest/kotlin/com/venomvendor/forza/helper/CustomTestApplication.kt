/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.helper

import com.venomvendor.forza.app.core.CustomApplication
import com.venomvendor.forza.app.util.EspressoIdlingResource
import com.venomvendor.forza.helper.di.appTestModule
import org.koin.core.context.loadKoinModules

class CustomTestApplication : CustomApplication() {

    override fun onCreate() {
        super.onCreate()

        EspressoIdlingResource.setDefaultIdlingResource()

        loadKoinModules(appTestModule)
    }
}
