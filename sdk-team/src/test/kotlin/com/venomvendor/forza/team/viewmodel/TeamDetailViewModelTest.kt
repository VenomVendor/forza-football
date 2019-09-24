/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.team.viewmodel

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.venomvendor.forza.core.di.coreModule
import com.venomvendor.forza.team.di.teamModule
import com.venomvendor.forza.team.helper.BaseTest
import com.venomvendor.forza.team.model.Team
import com.venomvendor.forza.team.repository.TeamRepository
import io.reactivex.Single
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.get
import org.mockito.Mockito
import org.mockito.Mockito.`when` as mockitoWhen

class TeamDetailViewModelTest : BaseTest() {

    override fun before() {
        super.before()
        startKoin {
            modules(listOf(coreModule, teamModule))
        }
    }

    @Test
    fun `getTeams With Results`() {
        val response: String = with(this).read("response.json")
        val teamObj = get<Gson>().fromJson<List<Team>>(
            response,
            object : TypeToken<List<Team>>() {}.type
        )

        val mockRepository = Mockito.mock(TeamRepository::class.java)

        mockitoWhen(mockRepository.getTeams())
            .thenReturn(
                Single.just(teamObj)
            )

        val randomUserViewModel = TeamViewModel(mockRepository)

        randomUserViewModel.getTeams()
            .test()
            .assertSubscribed()
            .assertComplete()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue {
                it.count() == 5
            }
    }

    @Test
    fun `getTeams With Error`() {
        val mockRepository = Mockito.mock(TeamRepository::class.java)

        mockitoWhen(mockRepository.getTeams())
            .thenReturn(
                Single.error(Exception("Test"))
            )

        val randomUserViewModel = TeamViewModel(mockRepository)

        randomUserViewModel.getTeams()
            .test()
            .assertSubscribed()
            .assertError {
                it.message == "Test"
            }
    }
}
