/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.core.data.factory

import androidx.annotation.CheckResult
import androidx.annotation.WorkerThread
import io.reactivex.Single

/**
 * Repository to be handled by [com.venomvendor.forza.core.data.RepositoryManager]
 *
 * @param <T> Data type received from server
 */
interface Repository<T> {

    /**
     * Get Cached data from storage
     *
     * @return Cached data or `null`
     */
    @CheckResult
    fun cachedData(): T?

    /**
     * Request to be executed if data is not available.
     *
     * @return Request or `null` if no request to be made to server.
     */
    @CheckResult
    fun request(): Single<T>

    /**
     * Save a copy of data to cache
     *
     * @param data data to be saved.
     */
    @WorkerThread
    @Throws(Exception::class)
    fun saveData(data: T)
}
