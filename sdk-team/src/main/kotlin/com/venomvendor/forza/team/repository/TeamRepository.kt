/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.team.repository

import androidx.annotation.CheckResult
import com.venomvendor.forza.core.data.RepositoryManager
import com.venomvendor.forza.team.db.TeamDatabase
import com.venomvendor.forza.team.factory.TeamService
import com.venomvendor.forza.team.model.Team
import com.venomvendor.forza.team.model.TeamDetail
import io.reactivex.Single
import org.koin.core.KoinComponent

class TeamRepository(
    private val apiService: TeamService,
    private val storage: TeamDatabase
) : KoinComponent {
    private val team: RepositoryManager<List<Team>> by lazy {
        // Create request object
        RepositoryManager.create(AllTeamRepository(storage.teamDao(), apiService))
    }

    // Create unique instance every-time, to support fetching of multiple teams in batch
    private val teamById: TeamByIdRepository
        get() = TeamByIdRepository(
            storage.teamDetailDao(),
            apiService
        )

    @CheckResult
    fun getTeams(): Single<List<Team>> {
        // Execute repository
        return team.execute()
    }

    @CheckResult
    fun getTeam(id: Int): Single<TeamDetail> {
        // Create request object
        return RepositoryManager.create(teamById.apply {
            this.id = id
        })
            // Execute repository
            .execute()
    }
}
