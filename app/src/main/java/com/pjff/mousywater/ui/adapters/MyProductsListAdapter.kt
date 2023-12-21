package com.pjff.mousywater.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pjff.mousywater.databinding.ItemListLayoutBinding
import com.pjff.mousywater.models.Product
import android.content.Context
import com.pjff.mousywater.ui.fragments.ProductsFragment
import com.pjff.mousywater.utils.GlideLoader


/**
 * A adapter class for products list items.
 */
// TODO Step 6: Add the parameter as products fragment as we cannot call the delete function of products fragment on the delete button click.
// START
open class MyProductsListAdapter(

    private val context: Context,
    private var list: ArrayList<Product>,
    private val fragment: ProductsFragment
) : RecyclerView.Adapter<MyProductsListAdapter.ViewHolder>() {
    class ViewHolder( val binding: ItemListLayoutBinding): RecyclerView.ViewHolder(binding.root){

        val ivItemImage = binding.ivItemImage

        fun bind(product: Product){
            with(binding){
                //estos son nuestros textview
                binding.apply {
                    //tvTitle , es como se llaman nuestras etiquetas
                    tvItemPrice.text = product.price
                    tvItemName.text = product.title

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

        GlideLoader(context).loadProductPicture(model.image, holder.ivItemImage)

    }

    /**
     * Gets the number of items in the list
     */

    override fun getItemCount(): Int = list.size

}