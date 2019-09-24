/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 23-Sep-2019.
 */

package com.venomvendor.forza.team.repository

import androidx.annotation.CheckResult
import com.venomvendor.forza.core.data.factory.Repository
import com.venomvendor.forza.team.dao.TeamDetailDao
import com.venomvendor.forza.team.factory.TeamService
import com.venomvendor.forza.team.model.TeamDetail
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent

class TeamByIdRepository(
    private val storage: TeamDetailDao,
    private val search: TeamService
) : Repository<TeamDetail>, KoinComponent {

    var id: Int = -1

    override fun cachedData(): TeamDetail? {
        return storage.getById(id)
    }

    @CheckResult
    override fun request(): Single<TeamDetail> {
        return search.getTeam(id)
            .doOnSuccess {
                if (it.isSuccess.not()) {
                    // TODO: Wrap in nice exception
                    throw RuntimeException(it.message ?: "Unknown Error Occurred")
                }
            }
            .doOnSuccess {
                saveData(it)
            }
    }

    @Throws(Exception::class)
    override fun saveData(data: TeamDetail) {
        // Do In parallel
        storage.insertAll(data)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}
