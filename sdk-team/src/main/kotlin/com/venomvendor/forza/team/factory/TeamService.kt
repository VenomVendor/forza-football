/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.team.factory

import com.venomvendor.forza.team.model.Team
import com.venomvendor.forza.team.model.TeamDetail

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Webservice to make team API calls
 */
interface TeamService {

    /**
     * Get all Teams
     * TODO: Change response object type &amp;
     * use custom [GSON] adapter to handle error object, response array in single Type
     *
     * @return SingleObservable to subscribe the result.
     */
    @GET("/teams/teams.json")
    fun getTeams(): Single<List<Team>>

    /**
     * Get TeamDetail detail by id
     * @param id Unique id for team
     */
    @GET("/teams/{id}/team.json")
    fun getTeam(@Path("id") id: Int): Single<TeamDetail>
}
