package com.pjff.mousywater.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
//Listo
/**
 * A data model class for User with required fields.
 */
// TODO Step 2: Make the user class parcelable.
// START
@Parcelize
data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val image: String = "",
    val mobile: Long = 0,
    val gender: String = "",
    val profileCompleted: Int = 0
) : Parcelable
// END