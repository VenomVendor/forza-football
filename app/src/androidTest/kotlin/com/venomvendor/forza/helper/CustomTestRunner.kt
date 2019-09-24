/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

@file:Suppress("unused")

package com.venomvendor.forza.helper

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class CustomTestRunner : AndroidJUnitRunner() {
    @Throws(
        ClassNotFoundException::class,
        IllegalAccessException::class,
        InstantiationException::class
    )
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, CustomTestApplication::class.java.name, context)
    }
}
