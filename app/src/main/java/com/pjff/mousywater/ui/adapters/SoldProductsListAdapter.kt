package com.pjff.mousywater.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pjff.mousywater.databinding.ItemListLayoutBinding
import com.pjff.mousywater.models.Product
import com.pjff.mousywater.models.SoldProduct
import com.pjff.mousywater.ui.activities.ProductDetailActivity
import com.pjff.mousywater.ui.activities.SoldProductActivity
import com.pjff.mousywater.ui.fragments.ProductsFragment
import com.pjff.mousywater.utils.Constants
import com.pjff.mousywater.utils.GlideLoader



open class SoldProductsListAdapter(


    private val context: Context,
    private var list: ArrayList<SoldProduct>
    //private val fragment: ProductsFragment
) : RecyclerView.Adapter<SoldProductsListAdapter.ViewHolder>() {
    class ViewHolder( val binding: ItemListLayoutBinding): RecyclerView.ViewHolder(binding.root){

        val ivItemImage = binding.ivItemImage
        val  ibDeleteProduct = binding.ibDeleteProduct

        fun bind(soldProduct: SoldProduct){
            with(binding){
                //estos son nuestros textview
                binding.apply {
                    //tvTitle , es como se llaman nuestras etiquetas
                    //tvItemPrice.text = product.price
                    tvItemPrice.text = "$${soldProduct.price}"
                    tvItemName.text = soldProduct.title

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
        val binding = ItemListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

        GlideLoader(context).loadProductPicture(model.image,holder.ivItemImage)
        holder.ibDeleteProduct.visibility = View.GONE

        // TODO Step 7: Assign the click event for sold product item.
        // START
        holder.ivItemImage.setOnClickListener {
            val intent = Intent(context, SoldProductActivity::class.java)
            intent.putExtra(Constants.EXTRA_SOLD_PRODUCT_DETAILS, model)
            context.startActivity(intent)
        }
        // END

    }

    /**
     * Gets the number of items in the list
     */

    override fun getItemCount(): Int = list.size

}
