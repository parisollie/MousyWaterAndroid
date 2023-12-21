package com.pjff.mousywater.ui.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pjff.mousywater.models.Product
import android.content.Context
import com.pjff.mousywater.databinding.ItemDashboardLayoutBinding
import com.pjff.mousywater.ui.fragments.DashboardFragment
import com.pjff.mousywater.utils.GlideLoader


/**
 * A adapter class for products list items.
 */
// TODO Step 6: Add the parameter as products fragment as we cannot call the delete function of products fragment on the delete button click.
// START
open class DashboardItemsListAdapter(

    private val context: Context,
    private var list: ArrayList<Product>,
    private val fragment: DashboardFragment
) : RecyclerView.Adapter<DashboardItemsListAdapter.ViewHolder>() {
    // TODO Step 2: Create a global variable for OnClickListener interface.
    // START
    // A global variable for OnClickListener interface.
    private var onClickListener: OnClickListener? = null
    // END
    class ViewHolder( val binding: ItemDashboardLayoutBinding): RecyclerView.ViewHolder(binding.root){

        val ivItemImage = binding.ivDashboardItemImage

        fun bind(product: Product){
            with(binding){
                //estos son nuestros textview
                binding.apply {
                    //tvTitle , es como se llaman nuestras etiquetas
                    tvDashboardItemPrice.text = product.price
                    tvDashboardItemTitle.text = product.title

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
        val binding = ItemDashboardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

        // TODO Step 5: Assign the on click event for item view and pass the required params in the on click function.
        // START
        holder.ivItemImage.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, model)
            }
        }
        // END

    }

    /**
     * Gets the number of items in the list
     */

    override fun getItemCount(): Int = list.size

    // TODO Step 3: Create A function for OnClickListener where the Interface is the expected parameter and assigned to the global variable.
    // START
    /**
     * A function for OnClickListener where the Interface is the expected parameter and assigned to the global variable.
     *
     * @param onClickListener
     */
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    // END


    // TODO Step 1: Create an interface for OnClickListener.
    /**
     * An interface for onclick items.
     */
    interface OnClickListener {

        // TODO Step 4: Define a function to get the required params when user clicks on the item view in the interface.
        // START
        fun onClick(position: Int, product: Product)
        // END
    }
    // END

}