/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 24-Sep-2019.
 */

package com.venomvendor.forza.app.all.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.venomvendor.forza.R
import com.venomvendor.forza.app.all.adapter.TeamListAdapter
import com.venomvendor.forza.app.common.BaseFragment
import com.venomvendor.forza.app.common.factory.OnItemClickListener
import com.venomvendor.forza.app.util.EspressoIdlingResource
import com.venomvendor.forza.team.model.Team
import com.venomvendor.forza.team.viewmodel.TeamViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.team_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Main View for listing Teams.
 */
class TeamListFragment : BaseFragment(), OnItemClickListener<Team> {

    private val teamViewModel: TeamViewModel by viewModel()

    private val disposable = CompositeDisposable()

    /* Adapter holding data. */
    private lateinit var teamAdapter: TeamListAdapter

    @get:LayoutRes
    override val layout = R.layout.team_list_fragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Initialize Listeners
        initListener()
    }

    override fun initViews(view: View) {
        // For performance
        team_list.setHasFixedSize(true)
        // Set layout type

        updateLayoutManager()

        // Create Adapter
        teamAdapter = TeamListAdapter(context!!)
        // Set adapter
        team_list.adapter = teamAdapter
    }

    private fun updateLayoutManager() {
        team_list.layoutManager = LinearLayoutManager(context)
    }

    /**
     * Initialize event listeners
     */
    private fun initListener() {
        getAllTeams()

        teamAdapter.setOnItemClickLister(this)

        refresh.setOnRefreshListener {
            getAllTeams()
        }
    }

    private fun getAllTeams() {
        EspressoIdlingResource.increment()
        progress_bar.visibility = View.VISIBLE

        disposable.add(
            teamViewModel.getTeams()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEvent { _, _ ->
                    resetProgress()
                }
                .subscribe({ teams ->
                    handleData(teams)
                }, { ex ->
                    handleError(ex)
                })
        )
    }

    /**
     * Handler for data received
     *
     * @param response data
     */
    private fun handleData(response: List<Team>) {
        // Update data.
        teamAdapter.submitList(response)
    }

    /**
     * TODO: Handle errors
     */
    private fun handleError(error: Throwable) {
        // Show error toast
        showToast(error.message!!)
    }

    private fun resetProgress() {
        // Update indicators
        progress_bar.visibility = View.GONE
        refresh.isRefreshing = false

        EspressoIdlingResource.decrement()
    }

    override fun onClick(item: Team, view: View, position: Int) {
        val action = TeamListFragmentDirections.actionTeamListFragmentToTeamDetailFragment(
            item
        )
        // Get Navigation controller
        Navigation.findNavController(view)
            // Navigate to next view with predefined action & data.
            .navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
