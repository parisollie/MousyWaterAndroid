package com.pjff.mousywater.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.ActivityCartListBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.Cart

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

    // TODO Step 5: Create a function to notify the success result of the cart items list from cloud firestore.
    // START
    /**
     * A function to notify the success result of the cart items list from cloud firestore.
     */
    fun successCartItemsList(cartList: ArrayList<Cart>) {

        // Hide progress dialog.
        hideProgressDialog()

        for (i in cartList) {

            Log.i("Cart Item Title", i.title)

        }
    }
    // END
}