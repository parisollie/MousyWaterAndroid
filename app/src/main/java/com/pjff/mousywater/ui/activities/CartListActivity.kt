package com.pjff.mousywater.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.ActivityCartListBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.Cart
import com.pjff.mousywater.ui.adapters.CartItemsListAdapter

class CartListActivity : BaseActivity()  {
    private lateinit var binding:ActivityCartListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        binding = ActivityCartListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO Step 3: Call the function to setup the action bar.
        // START
        setupActionBar()
        // END
    }

    // TODO Step 8: Override the onResume function and call the function to getCartItemsList.
    // START
    override fun onResume() {
        super.onResume()

        getCartItemsList()
    }
    // END


    // TODO Step 2: Create a function to setup the action bar.
    // START
    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarCartListActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        binding.toolbarCartListActivity.setNavigationOnClickListener { onBackPressed() }
    }
    // END

    // TODO Step 7: Create a function to get the list of cart items in the activity.
    // START
    private fun getCartItemsList() {

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getCartList(this@CartListActivity)
    }
    // END

    /**
     * A function to notify the success result of the cart items list from cloud firestore.
     *
     * @param cartList
     */
    fun successCartItemsList(cartList: ArrayList<Cart>) {

        // Hide progress dialog.
        hideProgressDialog()

        // TODO Step 2: Remove the for loop and display the list of cart items in the recycler view along with total amount.
        // START

        /*for (i in cartList) {

            Log.i("Cart Item Title", i.title)

        }*/

        if (cartList.size > 0) {

            binding.rvCartItemsList.visibility = View.VISIBLE
            binding.llCheckout.visibility = View.VISIBLE
            binding.tvNoCartItemFound.visibility = View.GONE

            binding.rvCartItemsList.layoutManager = LinearLayoutManager(this@CartListActivity)
            binding.rvCartItemsList.setHasFixedSize(true)

            val cartListAdapter = CartItemsListAdapter(this@CartListActivity, cartList)
            binding.rvCartItemsList.adapter = cartListAdapter

            var subTotal: Double = 0.0

            for (item in cartList) {

                val price = item.price.toDouble()
                val quantity = item.cart_quantity.toInt()

                subTotal += (price * quantity)
            }

            binding.tvSubTotal.text = "$$subTotal"
            // Here we have kept Shipping Charge is fixed as $10 but in your case it may cary. Also, it depends on the location and total amount.
            binding.tvShippingCharge.text = "$10.0"

            if (subTotal > 0) {
                binding.llCheckout.visibility = View.VISIBLE

                val total = subTotal + 10
                binding.tvTotalAmount.text = "$$total"
            } else {
                binding.llCheckout.visibility = View.GONE
            }

        } else {
            binding.rvCartItemsList.visibility = View.GONE
            binding.llCheckout.visibility = View.GONE
            binding.tvNoCartItemFound.visibility = View.VISIBLE
        }
    }
    // END
}