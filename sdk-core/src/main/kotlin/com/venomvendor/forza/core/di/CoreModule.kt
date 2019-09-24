/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.core.di

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import com.venomvendor.forza.core.annotation.Mandatory
import com.venomvendor.forza.core.data.RepositoryManager
import com.venomvendor.forza.core.data.factory.Repository
import com.venomvendor.forza.core.helper.CoreConstant.Companion.QUALIFIER_BASE_URL
import com.venomvendor.forza.core.helper.CoreConstant.Companion.QUALIFIER_ROOM
import com.venomvendor.forza.core.helper.CoreConstant.Companion.QUALIFIER_SHARED_PREF
import com.venomvendor.forza.core.network.NetworkManager
import com.venomvendor.forza.core.storage.RoomManager
import com.venomvendor.forza.core.storage.SharedPreferencesManager
import com.venomvendor.gson.NullDefenseTypeAdapterFactory
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://android-exam.s3-eu-west-1.amazonaws.com"

/**
 * Provides all dependencies in current core module.
 */
val coreModule = module {

    /**
     * Provides [Gson] to generate objects from Json.
     */
    single {
        val gsonBuilder = GsonBuilder()

        get<List<TypeAdapterFactory>>().forEach {
            gsonBuilder.registerTypeAdapterFactory(it)
        }

        gsonBuilder.create()
    }

    /**
     * Provides [Gson] to generate objects from Json.
     */
    factory {
        GsonConverterFactory.create(get()) as Converter.Factory
    }

    /**
     * Provides [TypeAdapterFactory] to attach with [Gson], this removes invalid object from response.
     */
    factory<List<TypeAdapterFactory>> {
        listOf(NullDefenseTypeAdapterFactory(Mandatory::class.java))
    }

    /**
     * Provides [RxJava2CallAdapterFactory] to create network response to Reactive streams.
     */
    factory {
        RxJava2CallAdapterFactory.create() as CallAdapter.Factory
    }

    /**
     * Provides Single Okhttp3 Client for http layer
     */
    single {
        OkHttpClient().newBuilder()
            .build()
    }

    /**
     * Provides Instance of networking client's base url
     */
    single(named(QUALIFIER_BASE_URL)) {
        BASE_URL
    }

    /**
     * Provides Instance of networking client.
     */
    single {
        Retrofit.Builder()
            .baseUrl(get<String>(named(QUALIFIER_BASE_URL)))
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .client(get())
            .build()
    }

    /**
     * Creates Repository Manager for the given repository
     */
    factory { (repository: Repository<*>) ->
        RepositoryManager(repository)
    }

    /**
     * Provider Network Manager
     */
    factory {
        NetworkManager()
    }

    /**
     * Provides Shared Preferences
     */
    factory(named(QUALIFIER_SHARED_PREF)) {
        SharedPreferencesManager()
    }

    /**
     * Provides Room/SQLite
     */
    single(named(QUALIFIER_ROOM)) {
        RoomManager()
    }
}
