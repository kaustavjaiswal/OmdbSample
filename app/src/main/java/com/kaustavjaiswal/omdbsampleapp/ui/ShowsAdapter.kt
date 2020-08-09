package com.kaustavjaiswal.omdbsampleapp.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kaustavjaiswal.omdbsampleapp.model.ShowData

/**
 * Adapter for the list of shows.
 */
class ShowsAdapter : PagingDataAdapter<ShowData, ViewHolder>(PHOTO_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ShowViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val showInfo = getItem(position)
        if (showInfo != null) {
            (holder as ShowViewHolder).bind(showInfo)
        }
    }

    companion object {
        private val PHOTO_DIFF = object : DiffUtil.ItemCallback<ShowData>() {
            override fun areItemsTheSame(oldItem: ShowData, newItem: ShowData): Boolean =
                oldItem.imdbID == newItem.imdbID

            override fun areContentsTheSame(oldItem: ShowData, newItem: ShowData): Boolean =
                oldItem == newItem
        }
    }
}
