/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.core.storage

import com.venomvendor.forza.core.storage.factory.Storage

/**
 * Yet to be implemented by storage provider
 */
class RoomManager : Storage {
    override fun <E> save(data: E) {
        TODO("not implemented")
    }

    override fun <E> update(data: E) {
        TODO("not implemented")
    }

    override fun <E> delete(data: E) {
        TODO("not implemented")
    }

    override fun <T> retrieve(clz: Class<T>): List<T>? {
        return null
    }
}
