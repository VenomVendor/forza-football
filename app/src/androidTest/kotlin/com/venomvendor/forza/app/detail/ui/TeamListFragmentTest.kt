/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 24-Sep-2019.
 */

package com.venomvendor.forza.app.detail.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.venomvendor.forza.R
import com.venomvendor.forza.helper.BaseTest
import com.venomvendor.forza.team.model.Team
import com.venomvendor.forza.team.model.TeamDetail
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import org.koin.core.get

class TeamListFragmentTest : BaseTest() {

    @Test
    fun getDataFromServerSuccessfullyWithResults() {
        val allTeams: String = with(this).read("response.json")
        val team = get<Gson>().fromJson<List<Team>>(
            allTeams,
            object : TypeToken<List<Team>>() {}.type
        ).first()

        val response: String = with(this).read("response-team-detail.json")
        val resObj = get<Gson>().fromJson(response, TeamDetail::class.java)

        val server = get<MockWebServer>()
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(response)
        )

        /* Launches Fragment directly with bundle */
        launchFragmentInContainer<TeamDetailFragment>(
            TeamDetailFragmentArgs(team = team).toBundle(),
            R.style.AppTheme
        )

        onView(withId(R.id.team_name))
            .check(matches(withText(resObj.name)))

        onView(withId(R.id.national))
            .check(matches(withText(resObj.national.toString())))
    }
}
