package com.pjff.mousywater.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.ItemAddressLayoutBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.Address
import com.pjff.mousywater.ui.activities.CartListActivity
import com.pjff.mousywater.utils.Constants
import com.pjff.mousywater.utils.GlideLoader


/**
 * A adapter class for products list items.
 */
// TODO Step 6: Add the parameter as products fragment as we cannot call the delete function of products fragment on the delete button click.
// START
open class AddressListAdapter(

    private val context: Context,
    private var list: ArrayList<Address>


) : RecyclerView.Adapter<AddressListAdapter.ViewHolder>() {
    class ViewHolder( val binding: ItemAddressLayoutBinding): RecyclerView.ViewHolder(binding.root){

        /*val ivItemImage = binding.ivCartItemImage
        val ibRemoveCartItem = binding.ibRemoveCartItem
        val ibAddCartItem = binding.ibAddCartItem
        val tvCartQuantity = binding.tvCartQuantity
        val ibDeleteCartItem = binding.ibDeleteCartItem*/


        fun bind(address: Address){
            with(binding){
                //estos son nuestros textview
                binding.apply {
                    //tvTitle , es como se llaman nuestras etiquetas

                    tvAddressFullName.text = address.name
                    tvAddressType.text = address.type
                    tvAddressDetails.text = "${address.address},${address.zipCode}"
                    tvAddressMobileNumber.text = address.mobileNumber

                }
            }
        }
    } // END

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAddressLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        val model = list[position]

    }

    /**
     * Gets the number of items in the list
     */

    override fun getItemCount(): Int = list.size

}