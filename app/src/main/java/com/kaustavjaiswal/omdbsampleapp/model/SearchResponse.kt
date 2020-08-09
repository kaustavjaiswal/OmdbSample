package com.kaustavjaiswal.omdbsampleapp.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@Keep
@JsonClass(generateAdapter = true)
data class SearchResponse(
    @Json(name = "Response")
    val response: String,
    @Json(name = "Search")
    val showData: List<ShowData>,
    @Json(name = "totalResults")
    val totalResults: String
) : Parcelable

