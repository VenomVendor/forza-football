/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 24-Sep-2019.
 */

package com.venomvendor.forza.app.detail.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.navigation.Navigation
import com.bumptech.glide.request.RequestOptions
import com.venomvendor.forza.R
import com.venomvendor.forza.app.common.BaseFragment
import com.venomvendor.forza.app.core.GlideApp
import com.venomvendor.forza.app.util.EspressoIdlingResource
import com.venomvendor.forza.core.helper.CoreConstant
import com.venomvendor.forza.team.model.Team
import com.venomvendor.forza.team.model.TeamDetail
import com.venomvendor.forza.team.viewmodel.TeamViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.team_detail_fragment.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.util.Objects

class TeamDetailFragment : BaseFragment() {

    @get:LayoutRes
    override val layout = R.layout.team_detail_fragment
    private val teamViewModel: TeamViewModel by viewModel()
    private val disposable = CompositeDisposable()
    private lateinit var team: Team
    private val glide by lazy { GlideApp.with(context!!) }
    private val baseUrl = get<String>(named(CoreConstant.QUALIFIER_BASE_URL))

    override fun initViews(view: View) {
        // Do nothing
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            team = TeamDetailFragmentArgs.fromBundle(it).team
            // Update data.
            setData()
            getData()
        }
        if (team == null) {
            // It no data is available, return to previous view
            Navigation.findNavController(Objects.requireNonNull<View>(view)).navigateUp()
        }
    }

    private fun setData() {
        team_name.text = team.name
        gender.text = team.gender
        national.text = team.national.toString()
        desc.text = getString(R.string.loading)

        // Load image
        glide.load("https://api.adorable.io/avatars/${team.id}")
            .centerCrop()
            .placeholder(R.drawable.circle_bg)
            .apply(RequestOptions.circleCropTransform())
            .into(media)
    }

    private fun getData() {
        EspressoIdlingResource.increment()
        disposable.add(
            teamViewModel.getTeam(team.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ detail ->
                    handleData(detail)
                }, { ex ->
                    handleError(ex)
                })
        )
    }

    private fun handleData(detail: TeamDetail) {
        team_name.text = detail.name
        gender.text = detail.gender
        national.text = detail.national.toString()
        desc.text = detail.description
        // Load image
        glide.load("${baseUrl}${detail.badgeUrl}")
            .fitCenter()
            .placeholder(R.drawable.circle_bg)
            .into(media)

        EspressoIdlingResource.decrement()
    }

    /**
     * TODO: Handle errors
     */
    private fun handleError(error: Throwable) {
        // Show error toast
        showToast(error.message!!)
        EspressoIdlingResource.decrement()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }
}
