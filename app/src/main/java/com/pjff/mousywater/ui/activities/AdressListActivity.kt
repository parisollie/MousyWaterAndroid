package com.pjff.mousywater.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.ActivityAdressListBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.Address

class AdressListActivity : BaseActivity() {
    private lateinit var binding:ActivityAdressListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        binding = ActivityAdressListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO Step 10: Call the setup action bar function.
        // START
        setupActionBar()
        // END

        // TODO Step 8: Assign the click event for the Add Address and launch the AddEditAddressActivity.
        // START
        binding.tvAddAddress.setOnClickListener {
            val intent = Intent(this@AdressListActivity, AddEditAddressActivity::class.java)
            startActivity(intent)
        } // END


        // TODO Step 6: Call the function to get the address list.
        // START
        getAddressList()
        // END
    }




    // TODO Step 9: Create a function to setup action bar.
    // START
    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarAddressListActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        binding.toolbarAddressListActivity.setNavigationOnClickListener { onBackPressed() }
    } // END




    // TODO Step 3: Create a function to get the success result of address list from cloud firestore.
    // START
    /**
     * A function to get the success result of address list from cloud firestore.
     *
     * @param addressList
     */
    fun successAddressListFromFirestore(addressList: ArrayList<Address>) {

        // Hide the progress dialog
        hideProgressDialog()


        // Print all the list of addresses in the log with name.
        for (i in addressList) {

            Log.i("Name and Address", "${i.name} ::  ${i.address}")
        }
    }
    // END



    // TODO Step 5: Create a function to get the list of address from cloud firestore.
    // START
    /**
     * A function to get the list of address from cloud firestore.
     */
    private fun getAddressList() {

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getAddressesList(this@AdressListActivity)
    }
    // END





}