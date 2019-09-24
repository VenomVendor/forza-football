/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 24-Sep-2019.
 */

package com.venomvendor.forza.app.all.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.venomvendor.forza.R
import com.venomvendor.forza.helper.BaseTest
import com.venomvendor.forza.helper.RecyclerViewItemCountAssertion.withItemCount
import com.venomvendor.forza.team.model.Team
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import org.koin.core.get

class TeamDetailFragmentTest : BaseTest() {

    @Test
    fun getDataFromServerSuccessfullyWithResults() {
        val response = with(this).read("response.json")
        val server = get<MockWebServer>()
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(response)
        )

        val resObj = get<Gson>().fromJson<List<Team>>(
            response,
            object : TypeToken<List<Team>>() {}.type
        )

        /* Launches Fragment directly */
        launchFragmentInContainer<TeamListFragment>(null, R.style.AppTheme)

        onView(withId(R.id.team_list))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(resObj.count() - 1))

        onView(withId(R.id.team_list))
            .check(withItemCount(resObj.count()))
    }
}
