package com.pjff.mousywater.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.ActivityAdressListBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.Address
import com.pjff.mousywater.ui.adapters.AddressListAdapter
import com.pjff.mousywater.utils.SwipeToDeleteCallback
import com.pjff.mousywater.utils.SwipeToEditCallback

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



    }


    override fun onResume() {
        super.onResume()
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


        // TODO Step 5: Populate the address list in the UI.
        // START
        if (addressList.size > 0) {

            binding.rvAddressList.visibility = View.VISIBLE
            binding.tvNoAddressFound.visibility = View.GONE

            binding.rvAddressList.layoutManager = LinearLayoutManager(this@AdressListActivity)
            binding.rvAddressList.setHasFixedSize(true)

            val addressAdapter = AddressListAdapter(this@AdressListActivity, addressList)
            binding.rvAddressList.adapter = addressAdapter

            // TODO Step 3: Add the swipe to edit feature.
            // START
            val editSwipeHandler = object : SwipeToEditCallback(this) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    // TODO Step 7: Call the notifyEditItem function of the adapter class.
                    // START
                    val adapter = binding.rvAddressList.adapter as AddressListAdapter
                    adapter.notifyEditItem(
                        this@AdressListActivity,
                        viewHolder.adapterPosition
                    )
                    // END
                }
            }

            val editItemTouchHelper = ItemTouchHelper(editSwipeHandler)
            editItemTouchHelper.attachToRecyclerView(binding.rvAddressList)

            val deleteSwipeHandler = object : SwipeToDeleteCallback(this) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    // Show the progress dialog.
                    showProgressDialog(resources.getString(R.string.please_wait))

                    FirestoreClass().deleteAddress(
                        this@AdressListActivity,
                        addressList[viewHolder.adapterPosition].id)
                }
            }
            val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
            deleteItemTouchHelper.attachToRecyclerView(binding.rvAddressList)

        } else {
            binding.rvAddressList.visibility = View.GONE
            binding.tvNoAddressFound.visibility = View.VISIBLE
        }
        // END



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



    /**
     * A function notify the user that the address is deleted successfully.
     */
    fun deleteAddressSuccess() {

        // Hide progress dialog.
        hideProgressDialog()

        Toast.makeText(
            this@AdressListActivity,
            resources.getString(R.string.err_your_address_deleted_successfully),
            Toast.LENGTH_SHORT
        ).show()

        getAddressList()
    }//END




}