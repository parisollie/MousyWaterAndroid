package com.pjff.mousywater.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.ActivityLoginBinding
import com.pjff.mousywater.databinding.ActivityProductDetailBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.Cart
import com.pjff.mousywater.models.Product
import com.pjff.mousywater.utils.Constants
import com.pjff.mousywater.utils.GlideLoader


class ProductDetailActivity : BaseActivity() , View.OnClickListener{

    // TODO Step 5: Create a global instance of the Product data class which will be initialized later on.
    // START
    private lateinit var mProductDetails: Product
    // END

    private lateinit var binding: ActivityProductDetailBinding
    // TODO Step 5: Create a global variable for product id.
    // START
    // A global variable for product id.
    private var mProductId: String = ""
    // END

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        binding =  ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO Step 6: Get the product id through the intent extra and print it in the log.
        // START
        if (intent.hasExtra(Constants.EXTRA_PRODUCT_ID)) {
            mProductId =
                intent.getStringExtra(Constants.EXTRA_PRODUCT_ID)!!
           // Log.i("Product Id", mProductId)
        } // END

        // TODO Step 7: Get the product owner id through intent.
        // START
        var productOwnerId: String = ""

        if (intent.hasExtra(Constants.EXTRA_PRODUCT_OWNER_ID)) {
            productOwnerId =
                intent.getStringExtra(Constants.EXTRA_PRODUCT_OWNER_ID)!!
        }
        // END
        setupActionBar()

        // TODO Step 8: Now we have the product owner id so if the product which is added by owner himself should not see the button Add To Cart.
        // START
        if (FirestoreClass().getCurrentUserID() == productOwnerId) {
            binding.btnAddToCart.visibility = View.GONE
            binding.btnGoToCart.visibility = View.GONE
        } else {
            binding.btnAddToCart.visibility = View.VISIBLE
        } // END

        // TODO Step 3: Assign the onClick event to the add to cart button.
        // START
        binding.btnAddToCart.setOnClickListener(this)
        binding.btnGoToCart.setOnClickListener(this)
        // END

        // TODO Step 6: Call the function to get the product details when the activity is launched.
        // START
        getProductDetails()
        // END
    }

    // TODO Step 1: Create a function to setup the action bar.
    // START
    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarProductDetailsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        binding.toolbarProductDetailsActivity.setNavigationOnClickListener { onBackPressed() }
    }
    // END

    // TODO Step 3: Create a function to notify the success result of the product details based on the product id.
    // START
    /**
     * A function to notify the success result of the product details based on the product id.
     *
     * @param product A model class with product details.
     */
    fun productDetailsSuccess(product: Product) {

        // TODO Step 6: Initialize the the variable
        // START
        mProductDetails = product
        // END

        // Hide Progress dialog.
        //hideProgressDialog()

        // Populate the product details in the UI.
        GlideLoader(this@ProductDetailActivity).loadProductPicture(
            product.image,
            binding.ivProductDetailImage
        )

        binding.tvProductDetailsTitle.text = product.title
        binding.tvProductDetailsPrice.text = "$${product.price}"
        binding.tvProductDetailsDescription.text = product.description
        binding.tvProductDetailsQuantity.text = product.stock_quantity


        // TODO Step 9: Call the function to check the product exist in the cart or not from the firestore class.
        // START
        // There is no need to check the cart list if the product owner himself is seeing the product details.
        if (FirestoreClass().getCurrentUserID() == product.user_id) {
            // Hide Progress dialog.
            hideProgressDialog()
        } else {
            FirestoreClass().checkIfItemExistInCart(this@ProductDetailActivity, mProductId)
        }
        // END
    }
    // END

    // TODO Step 5: Create a function to call the firestore class function that will get the product details from cloud firestore based on the product id.
    // START
    /**
     * A function to call the firestore class function that will get the product details from cloud firestore based on the product id.
     */
    private fun getProductDetails() {

        // Show the product dialog
        showProgressDialog(resources.getString(R.string.please_wait))

        // Call the function of FirestoreClass to get the product details.
        FirestoreClass().getProductDetails(this@ProductDetailActivity, mProductId)
    }//END

    // TODO Step 8 : Create a function to prepare the cart item to add it to the cart.
    // START
    /**
     * A function to prepare the cart item to add it to the cart.
     */
    private fun addToCart() {

        val addToCart = Cart(
            FirestoreClass().getCurrentUserID(),
            mProductId,
            mProductDetails.title,
            mProductDetails.price,
            mProductDetails.image,
            Constants.DEFAULT_CART_QUANTITY
        )

        // TODO Step 5: Call the function of Firestore class to add the cart item to the cloud firestore along with the required params.
        // START
        // Show the progress dialog
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().addCartItems(this@ProductDetailActivity, addToCart)
        // END
    } // END

    override fun onClick(v: View?) {
        // TODO Step 9: Handle the click event of the Add to cart button and call the addToCart function.
        // START
        if (v != null) {
            when (v.id) {

                R.id.btn_add_to_cart -> {
                    addToCart()
                }
            }
        }
        // END
    }// END


    // TODO Step 3: Create a function to notify the success result of item added to the to cart.\
    // START
    fun addToCartSuccess() {
        // Hide the progress dialog.
        hideProgressDialog()

        Toast.makeText(
            this@ProductDetailActivity,
            resources.getString(R.string.success_message_item_added_to_cart),
            Toast.LENGTH_SHORT
        ).show()

        binding.btnAddToCart.visibility = View.GONE
        binding.btnGoToCart.visibility = View.VISIBLE
    }
    // END


    // TODO Step 7: Create a function to notify the success result of item exists in the cart.
    // START
    /**
     * A function to notify the success result of item exists in the cart.
     */
    fun productExistsInCart() {

        // Hide the progress dialog.
        hideProgressDialog()

        // Hide the AddToCart button if the item is already in the cart.
        binding.btnAddToCart.visibility = View.GONE
        // Show the GoToCart button if the item is already in the cart. User can update the quantity from the cart list screen if he wants.
        binding.btnGoToCart.visibility = View.VISIBLE
    }
    // END




}