/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.team.viewmodel

import androidx.annotation.CheckResult
import androidx.lifecycle.ViewModel
import com.venomvendor.forza.team.model.Team
import com.venomvendor.forza.team.model.TeamDetail
import com.venomvendor.forza.team.repository.TeamRepository
import io.reactivex.Single
import org.koin.core.KoinComponent

class TeamViewModel(
    private val teamRepo: TeamRepository
) : ViewModel(), KoinComponent {
    /**
     * THIS API WRAPS **DATA**
     * ******************************************
     * Inside [Single]. Error is propagated as is.
     * ******************************************
     * Get list of TeamDetail
     *
     * @return List of [Team] wrapped inside response.
     */
    @CheckResult
    fun getTeams(): Single<List<Team>> {
        return teamRepo.getTeams()
    }

    @CheckResult
    fun getTeam(id: Int): Single<TeamDetail> {
        return teamRepo.getTeam(id)
    }
}
