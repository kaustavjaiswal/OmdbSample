package com.kaustavjaiswal.omdbsampleapp.utilities

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kaustavjaiswal.omdbsampleapp.R

/**
 * Helper extension to load the image into the RecyclerView item using Glide
 */
fun ImageView.loadImageGlide(url: String, placeholder: Int = R.drawable.img_place_holder) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .placeholder(placeholder)
        .error(placeholder)
        .into(this)
}