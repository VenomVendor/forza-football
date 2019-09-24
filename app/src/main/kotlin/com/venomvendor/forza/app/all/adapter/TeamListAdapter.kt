/*
 * Copyright (c) 2019 VenomVendor. All rights reserved.
 * Created by VenomVendor on 16-Sep-2019.
 */

package com.venomvendor.forza.app.all.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.venomvendor.forza.R
import com.venomvendor.forza.app.common.factory.OnItemClickListener
import com.venomvendor.forza.app.core.GlideApp
import com.venomvendor.forza.app.util.DiffUtilHelper
import com.venomvendor.forza.team.model.Team

/**
 * Recycler Adapter for displaying list of results.
 */
internal class TeamListAdapter(context: Context) :
    ListAdapter<Team, TeamListAdapter.TeamViewHolder>(DiffUtilHelper.PERSON_DIFF) {

    private val glide by lazy { GlideApp.with(context) }

    // Event listener
    private var itemClickListener: OnItemClickListener<Team>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context)
            // Update item view
            .inflate(R.layout.team_item, parent, false)

        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        // Get item
        val team = getItem(position)

        // Update views
        holder.teamName.text = team.name

        holder.teamType.text = team.gender
        holder.teamId.text = team.id.toString()

        // Load image
        glide.load("https://api.adorable.io/avatars/${team.id}")
            .centerCrop()
            // place holder until image loads
            .placeholder(R.drawable.circle_bg)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.media)
    }

    fun setOnItemClickLister(listener: OnItemClickListener<Team>?) {
        itemClickListener = listener
    }

    /**
     * View Holder pattern, used by recycler view.
     */
    internal inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        internal val teamName = itemView.findViewById<TextView>(R.id.team_name)
        internal val teamType = itemView.findViewById<TextView>(R.id.team_type)
        internal val teamId = itemView.findViewById<TextView>(R.id.team_id)
        internal val media = itemView.findViewById<ImageView>(R.id.media)

        init {
            // Add event listener
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val selectedPos = adapterPosition
            if (selectedPos == RecyclerView.NO_POSITION) {
                return
            }

            // Pass on to root level event listener
            itemClickListener?.onClick(getItem(selectedPos), view, selectedPos)
        }
    }
}
