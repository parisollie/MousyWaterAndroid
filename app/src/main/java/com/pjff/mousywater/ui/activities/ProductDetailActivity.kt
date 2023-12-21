package com.pjff.mousywater.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.ActivityLoginBinding
import com.pjff.mousywater.databinding.ActivityProductDetailBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.Product
import com.pjff.mousywater.utils.Constants
import com.pjff.mousywater.utils.GlideLoader


class ProductDetailActivity : BaseActivity() {

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
            Log.i("Product Id", mProductId)
        }
        // END

        setupActionBar()

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

        // Hide Progress dialog.
        hideProgressDialog()

        // Populate the product details in the UI.
        GlideLoader(this@ProductDetailActivity).loadProductPicture(
            product.image,
            binding.ivProductDetailImage
        )

        binding.tvProductDetailsTitle.text = product.title
        binding.tvProductDetailsPrice.text = "$${product.price}"
        binding.tvProductDetailsDescription.text = product.description
        binding.tvProductDetailsQuantity.text = product.stock_quantity
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
    }
    // END



}