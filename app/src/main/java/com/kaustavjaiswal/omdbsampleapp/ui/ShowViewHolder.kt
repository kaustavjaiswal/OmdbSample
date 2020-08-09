package com.kaustavjaiswal.omdbsampleapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaustavjaiswal.omdbsampleapp.R
import com.kaustavjaiswal.omdbsampleapp.model.ShowData
import com.kaustavjaiswal.omdbsampleapp.utilities.loadImageGlide

/**
 * View Holder for a [ShowData] RecyclerView list item.
 */
class ShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val showView: ImageView = view.findViewById(R.id.image_holder)
    private val showTitle: TextView = view.findViewById(R.id.image_title)
    private val showYear: TextView = view.findViewById(R.id.year)

    fun bind(showData: ShowData?) {
        if (showData == null) {
            val resources = itemView.resources
            showTitle.text = resources.getString(R.string.loading)
        } else {
            showView.loadImageGlide(showData.poster)
            showTitle.apply {
                text = showData.title
                isSelected = true
                setHorizontallyScrolling(true)
            }
            showYear.apply {
                text = showData.year
                isSelected = true
                setHorizontallyScrolling(true)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): ShowViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.show_view_item, parent, false)
            return ShowViewHolder(view)
        }
    }
}
