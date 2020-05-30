package com.example.ituness

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
data class ReturnedData(
    val resultCount : Int,
    val results : List<Song>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.createTypedArrayList(Song) as ArrayList<Song>
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(resultCount)
        parcel.writeTypedList(results)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReturnedData> {
        override fun createFromParcel(parcel: Parcel): ReturnedData {
            return ReturnedData(parcel)
        }

        override fun newArray(size: Int): Array<ReturnedData?> {
            return arrayOfNulls(size)
        }
    }
}