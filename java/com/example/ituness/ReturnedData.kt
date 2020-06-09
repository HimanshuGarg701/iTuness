package com.example.ituness

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReturnedData(
    val resultCount : Int,
    val results : List<Song>
) : Parcelable {

}