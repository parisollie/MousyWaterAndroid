package com.pjff.mousywater.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.ActivityCheckoutBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.Address
import com.pjff.mousywater.models.Cart
import com.pjff.mousywater.models.Order
import com.pjff.mousywater.models.Product
import com.pjff.mousywater.ui.adapters.CartItemsListAdapter
import com.pjff.mousywater.utils.Constants

class CheckoutActivity : BaseActivity() {
    // TODO Step 6: Create a global variable for Order details.
    // START
    // A global variable for Order details.
    private lateinit var mOrderDetails: Order
    // END
    // TODO Step 3: Create a global variables for SubTotal and Total Amount.
    // START
    // A global variable for the SubTotal Amount.
    private var mSubTotal: Double = 0.0

    // A global variable for the Total Amount.
    private var mTotalAmount: Double = 0.0
    // END
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
        }// END

        // TODO Step 11: Assign a click event to the btn place order and call the function.
        // START
        binding.btnPlaceOrder.setOnClickListener {
            placeAnOrder()
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

                mSubTotal += (price * quantity)
            }
        }


        binding.tvCheckoutSubTotal.text = "$$mSubTotal"
        // Here we have kept Shipping Charge is fixed as $10 but in your case it may cary. Also, it depends on the location and total amount.
        binding.tvCheckoutShippingCharge.text = "$1.0"

        if (mSubTotal > 0) {
            binding.llCheckoutPlaceOrder.visibility = View.VISIBLE

            val mTotalAmount = mSubTotal + 10
            binding.tvCheckoutTotalAmount.text = "$$mTotalAmount"
        } else {
            binding.llCheckoutPlaceOrder.visibility = View.GONE
        }






        // END
    } // END


    // TODO Step 2: Create a function to prepare the Order details to place an order.
    // START
    /**
     * A function to prepare the Order details to place an order.
     */
    private fun placeAnOrder() {

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        if(mAddressDetails != null){

            // TODO Step 5: Now prepare the order details based on all the required details.
            // START
            mOrderDetails = Order(
                FirestoreClass().getCurrentUserID(),
                mCartItemsList,
                mAddressDetails!!,
                "My order ${System.currentTimeMillis()}",
                mCartItemsList[0].image,
                mSubTotal.toString(),
                "1.0", // The Shipping Charge is fixed as $10 for now in our case.
                mTotalAmount.toString(),
                // TODO Step 5: Pass the value as current timestamp in the required param for order date.
                // START
                System.currentTimeMillis()
                // END



            )
            // TODO Step 10: Call the function to place the order in the cloud firestore.
            // START
            FirestoreClass().placeOrder(this@CheckoutActivity, mOrderDetails)
            // END

        }
    }
    // END


    // TODO Step 8: Create a function to notify the success result of the order placed.
    // START
    /**
     * A function to notify the success result of the order placed.
     */
    fun orderPlacedSuccess() {

        // TODO Step 5: Move the below code to "allDetailsUpdatedSuccessfully" function and call the function to update the details after placing the order successfully.
        // START
        /*hideProgressDialog()

        Toast.makeText(this@CheckoutActivity, "Your order placed successfully.", Toast.LENGTH_SHORT)
            .show()

        val intent = Intent(this@CheckoutActivity, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()*/

        FirestoreClass().updateAllDetails(this@CheckoutActivity, mCartItemsList,mOrderDetails)
        // END
    } // END



    // TODO Step 3: Create a function to notify the success result after updating all the required details.
    // START
    /**
     * A function to notify the success result after updating all the required details.
     */
    fun allDetailsUpdatedSuccessfully() {

        // TODO Step 6: Move the piece of code from OrderPlaceSuccess to here.
        // START
        // Hide the progress dialog.
        hideProgressDialog()

        Toast.makeText(this@CheckoutActivity, "Your order placed successfully.", Toast.LENGTH_SHORT)
            .show()

        val intent = Intent(this@CheckoutActivity, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
        // END
    }






}