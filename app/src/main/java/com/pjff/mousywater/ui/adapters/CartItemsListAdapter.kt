package com.pjff.mousywater.ui.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.ItemCartLayoutBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.Cart
import com.pjff.mousywater.ui.activities.CartListActivity
import com.pjff.mousywater.utils.Constants
import com.pjff.mousywater.utils.GlideLoader



/**
 * A adapter class for products list items.
 */
// TODO Step 6: Add the parameter as products fragment as we cannot call the delete function of products fragment on the delete button click.
// START
open class CartItemsListAdapter(

    private val context: Context,
    private var list: ArrayList<Cart>


) : RecyclerView.Adapter<CartItemsListAdapter.ViewHolder>() {
    class ViewHolder( val binding: ItemCartLayoutBinding): RecyclerView.ViewHolder(binding.root){

        val ivItemImage = binding.ivCartItemImage
        val ibRemoveCartItem = binding.ibRemoveCartItem
        val ibAddCartItem = binding.ibAddCartItem
        val tvCartQuantity = binding.tvCartQuantity
        val ibDeleteCartItem = binding.ibDeleteCartItem


        fun bind(cart: Cart){
            with(binding){
                //estos son nuestros textview
                binding.apply {
                    //tvTitle , es como se llaman nuestras etiquetas
                    tvCartItemPrice.text = cart.price
                    tvCartQuantity.text = cart.cart_quantity
                    tvCartItemTitle.text = cart.title



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
        val binding = ItemCartLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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


        // TODO Step 1: Show the text Out of Stock when cart quantity is zero.
        // START
        if (model.cart_quantity == "0") {
            holder.ibRemoveCartItem.visibility = View.GONE
            holder.ibAddCartItem.visibility = View.GONE

            holder.tvCartQuantity.text =
                context.resources.getString(R.string.lbl_out_of_stock)

            holder.tvCartQuantity.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorSnackBarError
                )
            )
        } else {
            holder.ibRemoveCartItem.visibility = View.VISIBLE
            holder.ibAddCartItem.visibility = View.VISIBLE

            holder.tvCartQuantity.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorSecondaryText
                )
            )
        } // END



        // TODO Step 3: Assign the onclick event to the ib_delete_cart_item.
        // START
        holder.ibDeleteCartItem.setOnClickListener {

            // TODO Step 7: Call the firestore class function to remove the item from cloud firestore.
            // START

            when (context) {
                is CartListActivity -> {
                    context.showProgressDialog(context.resources.getString(R.string.please_wait))
                }
            }

            FirestoreClass().removeItemFromCart(context, model.id)
            // END
        }
        // END




        // TODO Step 1: Assign the click event to the ib_remove_cart_item.
        // START
        holder.ibRemoveCartItem.setOnClickListener {

            // TODO Step 6: Call the update or remove function of firestore class based on the cart quantity.
            // START
            if (model.cart_quantity == "1") {
                FirestoreClass().removeItemFromCart(context, model.id)
            } else {

                val cartQuantity: Int = model.cart_quantity.toInt()

                val itemHashMap = HashMap<String, Any>()

                itemHashMap[Constants.CART_QUANTITY] = (cartQuantity - 1).toString()

                // Show the progress dialog.

                if (context is CartListActivity) {
                    context.showProgressDialog(context.resources.getString(R.string.please_wait))
                }

                FirestoreClass().updateMyCart(context, model.id, itemHashMap)
            }
            // END
        }
        // END

        // TODO Step 7: Assign the click event to the ib_add_cart_item.
        // START
        holder.ibAddCartItem.setOnClickListener {

            // TODO Step 8: Call the update function of firestore class based on the cart quantity.
            // START
            val cartQuantity: Int = model.cart_quantity.toInt()

            if (cartQuantity < model.stock_quantity.toInt()) {

                val itemHashMap = HashMap<String, Any>()

                itemHashMap[Constants.CART_QUANTITY] = (cartQuantity + 1).toString()

                // Show the progress dialog.
                if (context is CartListActivity) {
                    context.showProgressDialog(context.resources.getString(R.string.please_wait))
                }

                FirestoreClass().updateMyCart(context, model.id, itemHashMap)
            } else {
                if (context is CartListActivity) {
                    context.showErrorSnackBar(
                        context.resources.getString(
                            R.string.msg_for_available_stock,
                            model.stock_quantity
                        ),
                        true
                    )
                }
            }
            // END
        }
        // END


        holder.ibDeleteCartItem.setOnClickListener {

            when (context) {
                is CartListActivity -> {
                    context.showProgressDialog(context.resources.getString(R.string.please_wait))
                }
            }

            FirestoreClass().removeItemFromCart(context, model.id)
        }










    }

    /**
     * Gets the number of items in the list
     */

    override fun getItemCount(): Int = list.size

}