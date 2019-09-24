/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.app.util

import androidx.recyclerview.widget.DiffUtil
import com.venomvendor.forza.team.model.Team

/**
 * Helper Utility for Recycler View's Data.
 */
class DiffUtilHelper private constructor() {

    init {
        throw UnsupportedOperationException("Instance should not be created")
    }

    companion object {

        val PERSON_DIFF: DiffUtil.ItemCallback<Team> =
            object : DiffUtil.ItemCallback<Team>() {
                override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
