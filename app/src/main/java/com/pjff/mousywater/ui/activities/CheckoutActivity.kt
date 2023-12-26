package com.pjff.mousywater.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.ActivityCheckoutBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.Address
import com.pjff.mousywater.models.Cart
import com.pjff.mousywater.models.Product
import com.pjff.mousywater.ui.adapters.CartItemsListAdapter
import com.pjff.mousywater.utils.Constants

class CheckoutActivity : BaseActivity() {
    // TODO Step 12: Global variable for cart items list.
    // START
    private lateinit var mCartItemsList: ArrayList<Cart>
    // END
    // TODO Step 7: Global variable for all product list.
    // START
    private lateinit var mProductsList: ArrayList<Product>
    // END
    // TODO Step 3: Create a global variable for the selected address details.
    // START
    // A global variable for the selected address details.
    private var mAddressDetails: Address? = null
    // END
    private lateinit var binding:ActivityCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO Step 7: Call the function to setup the action bar.
        // START
        setupActionBar()
        // END

        // TODO Step 4: Get the selected address details through intent.
        // START
        if (intent.hasExtra(Constants.EXTRA_SELECTED_ADDRESS)) {
            mAddressDetails =
                intent.getParcelableExtra<Address>(Constants.EXTRA_SELECTED_ADDRESS)!!
        }
        // END

        // TODO Step 5: Set the selected address details to UI that is received through intent.
        // START
        if (mAddressDetails != null) {
            binding.tvCheckoutAddressType.text = mAddressDetails?.type
            binding.tvCheckoutFullName.text = mAddressDetails?.name
            binding.tvCheckoutAddress.text = "${mAddressDetails!!.address}, ${mAddressDetails!!.zipCode}"
            binding.tvCheckoutAdditionalNote.text = mAddressDetails?.additionalNote

            if (mAddressDetails?.otherDetails!!.isNotEmpty()) {
                binding.tvCheckoutOtherDetails.text = mAddressDetails?.otherDetails
            }
            binding.tvMobileNumber.text = mAddressDetails?.mobileNumber
        }
        // END

        // TODO Step 16: Call the function to get the product list.
        // START
        getProductList()
        // END



    }

    // TODO Step 6: Create a function to setup the action bar.
    // START
    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarCheckoutActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        binding.toolbarCheckoutActivity.setNavigationOnClickListener { onBackPressed() }



    } // END


    // TODO Step 2: Create a function to get product list to compare it with the cart items stock.
    // START
    /**
     * A function to get product list to compare the current stock with the cart items.
     */
    private fun getProductList() {

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getAllProductsList(this@CheckoutActivity)
    } // END



    // TODO Step 4: Create a function to get the success result of product list.
    // START
    /**
     * A function to get the success result of product list.
     *
     * @param productsList
     */
    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {

        // TODO Step 8: Initialize the global variable of all product list.
        // START
        mProductsList = productsList
        // END

        // TODO Step 10: Call the function to get the latest cart items.
        // START
        getCartItemsList()
        // END
    }
    // END



    // TODO Step 9: Create a function to get the list of cart items in the activity.
    /**
     * A function to get the list of cart items in the activity.
     */
    private fun getCartItemsList() {

        FirestoreClass().getCartList(this@CheckoutActivity)
    }//END


    // TODO Step 11: Create a function to notify the success result of the cart items list from cloud firestore.
    // START
    /**
     * A function to notify the success result of the cart items list from cloud firestore.
     *
     * @param cartList
     */
    fun successCartItemsList(cartList: ArrayList<Cart>) {

        // Hide progress dialog.
        hideProgressDialog()


        // TODO Step 1: Update the stock quantity in the cart list from the product list.
        // START
        for (product in mProductsList) {
            for (cart in cartList) {
                if (product.product_id == cart.product_id) {
                    cart.stock_quantity = product.stock_quantity
                }
            }
        } // END
        // TODO Step 13: Initialize the cart list.
        // START
        mCartItemsList = cartList
        // END



        // TODO Step 2: Populate the cart items in the UI.
        // START
        binding.rvCartListItems.layoutManager = LinearLayoutManager(this@CheckoutActivity)
        binding.rvCartListItems.setHasFixedSize(true)

        // TODO Step 5: Pass the required param.
        val cartListAdapter = CartItemsListAdapter(this@CheckoutActivity, mCartItemsList, false)
        binding.rvCartListItems.adapter = cartListAdapter
        // END

        // TODO Step 9: Calculate the subtotal and Total Amount.
        // START
        var subTotal: Double = 0.0

        for (item in mCartItemsList) {

            val availableQuantity = item.stock_quantity.toInt()

            if (availableQuantity > 0) {
                val price = item.price.toDouble()
                val quantity = item.cart_quantity.toInt()

                subTotal += (price * quantity)
            }
        }


        binding.tvCheckoutSubTotal.text = "$$subTotal"
        // Here we have kept Shipping Charge is fixed as $10 but in your case it may cary. Also, it depends on the location and total amount.
        binding.tvCheckoutShippingCharge.text = "$10.0"

        if (subTotal > 0) {
            binding.llCheckoutPlaceOrder.visibility = View.VISIBLE

            val total = subTotal + 10
            binding.tvCheckoutTotalAmount.text = "$$total"
        } else {
            binding.llCheckoutPlaceOrder.visibility = View.GONE
        }
        // END






    }
    // END







}