/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.team.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.venomvendor.forza.core.di.coreModule
import com.venomvendor.forza.team.dao.FakeDao
import com.venomvendor.forza.team.dao.TeamDao
import com.venomvendor.forza.team.dao.TeamDetailDao
import com.venomvendor.forza.team.db.TeamDatabase
import com.venomvendor.forza.team.di.teamModule
import com.venomvendor.forza.team.factory.TeamService
import com.venomvendor.forza.team.helper.BaseTest
import com.venomvendor.forza.team.model.Team
import com.venomvendor.forza.team.model.TeamDetail
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.get
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.`when` as mockitoWhen

class TeamDetailRepositoryTest : BaseTest() {

    override fun before() {
        super.before()
        startKoin {
            modules(listOf(coreModule, teamModule))
        }
    }

    @Test
    fun `teamTeams With Results`() {
        val response: String = with(this).read("response.json")
        val teamObj = get<Gson>().fromJson<List<Team>>(
            response,
            object : TypeToken<List<Team>>() {}.type
        )

        val team: TeamService = Mockito.mock(TeamService::class.java)
        val teamDao = Mockito.mock(TeamDao::class.java)

        val fakeDao: TeamDatabase = FakeDao(teamDao, null)
        val teamRepository = TeamRepository(team, fakeDao)

        mockitoWhen(teamDao.getAll())
            .thenReturn(listOf())

        doReturn(Completable.complete())
            .`when`(teamDao).insert(teamObj)

        mockitoWhen(team.getTeams())
            .thenReturn(Single.just(teamObj))

        teamRepository.getTeams()
            .test()
            .assertSubscribed()
            .assertComplete()
            .assertNoErrors()
            .assertValue {
                it.count() == 5
            }
    }

    @Test
    fun `teamTeam With Result`() {
        val response: String = with(this).read("response-team-detail.json")
        val teamObj = get<Gson>().fromJson(response, TeamDetail::class.java)

        val team = Mockito.mock(TeamService::class.java)
        val teamDetailDao = Mockito.mock(TeamDetailDao::class.java)

        val fakeDao: TeamDatabase = FakeDao(null, teamDetailDao)
        val teamRepository = TeamRepository(team, fakeDao)

        mockitoWhen(teamDetailDao.getAll())
            .thenReturn(null)

        doReturn(Completable.complete())
            .`when`(teamDetailDao).insertAll(teamObj)

        mockitoWhen(team.getTeam(anyInt()))
            .thenReturn(Single.just(teamObj))

        teamRepository.getTeam(3)
            .test()
            .assertSubscribed()
            .assertComplete()
            .assertNoErrors()
            .assertValue {
                it.national
            }
            .assertValue {
                it.name == "Sweden"
            }
    }

    @Test
    fun `teamTeam With Error`() {
        val response: String = with(this).read("error.json")
        val teamObj = get<Gson>().fromJson(response, TeamDetail::class.java)

        val team = Mockito.mock(TeamService::class.java)
        val teamDetailDao = Mockito.mock(TeamDetailDao::class.java)

        val fakeDao: TeamDatabase = FakeDao(null, teamDetailDao)
        val teamRepository = TeamRepository(team, fakeDao)

        mockitoWhen(teamDetailDao.getAll())
            .thenReturn(null)

        doReturn(Completable.complete())
            .`when`(teamDetailDao).insertAll(teamObj)

        mockitoWhen(team.getTeam(anyInt()))
            .thenReturn(Single.just(teamObj))

        teamRepository.getTeam(3)
            .test()
            .assertSubscribed()
            .assertError {
                it.message == "Error Message"
            }
    }
}
