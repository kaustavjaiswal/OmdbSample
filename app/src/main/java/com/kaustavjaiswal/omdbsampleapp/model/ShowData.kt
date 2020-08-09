package com.kaustavjaiswal.omdbsampleapp.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@Keep
@Entity(tableName = "showData")
@JsonClass(generateAdapter = true)
data class ShowData(
    @PrimaryKey
    @Json(name = "imdbID")
    val imdbID: String,
    @Json(name = "Poster")
    val poster: String,
    @Json(name = "Title")
    val title: String,
    @Json(name = "Type")
    val type: String,
    @Json(name = "Year")
    val year: String
) : Parcelable