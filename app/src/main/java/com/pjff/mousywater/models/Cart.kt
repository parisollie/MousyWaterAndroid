package com.pjff.mousywater.models


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A data model class for Cart item with required fields.
 */
@Parcelize
data class Cart(
    val user_id: String = "",
    // TODO Step 1: Add a param as product owner.
    // START
    val product_owner_id: String = "",
    // END
    val product_id: String = "",
    val title: String = "",
    val price: String = "",
    val image: String = "",
    var cart_quantity: String = "",
    var stock_quantity: String = "",
    var id: String = "",
) : Parcelable