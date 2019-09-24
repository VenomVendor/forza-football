/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 23-Sep-2019.
 */

package com.venomvendor.forza.team.repository

import com.venomvendor.forza.core.data.factory.Repository
import com.venomvendor.forza.team.dao.TeamDao
import com.venomvendor.forza.team.factory.TeamService
import com.venomvendor.forza.team.model.Team
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent

class AllTeamRepository(
    private val storage: TeamDao,
    private val search: TeamService
) : Repository<List<Team>>, KoinComponent {

    override fun cachedData(): List<Team>? {
        storage.getAll().run {
            if (isEmpty()) {
                return null
            }
            return this
        }
    }

    override fun request(): Single<List<Team>> {
        return search.getTeams()
            .doOnSuccess {
                if (it.isEmpty()) {
                    // TODO: Wrap in nice exception
                    throw RuntimeException("Unknown Error Occurred")
                }
            }
            .doOnSuccess {
                saveData(it)
            }
    }

    @Throws(Exception::class)
    override fun saveData(data: List<Team>) {
        // Do In parallel
        storage.insert(data)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}
