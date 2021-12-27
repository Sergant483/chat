package com.senla.chat.models

import android.os.Parcel
import android.os.Parcelable

data class SearchTerms(
    var yourGender: Gender,
    var yourAge: Array<Int>,
    var otherPersonGender: Gender,
    var otherPersonAge: Array<Int>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readSerializable() as Gender ,
        parcel.readArray(Int::class.java.classLoader) as Array<Int>,
        parcel.readSerializable() as Gender,
        parcel.readArray(Int::class.java.classLoader) as Array<Int>
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(yourGender)
        parcel.writeSerializable(otherPersonGender)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchTerms> {
        override fun createFromParcel(parcel: Parcel): SearchTerms {
            return SearchTerms(parcel)
        }

        override fun newArray(size: Int): Array<SearchTerms?> {
            return arrayOfNulls(size)
        }
    }

}