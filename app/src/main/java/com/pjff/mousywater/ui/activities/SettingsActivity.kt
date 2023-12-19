package com.pjff.mousywater.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pjff.mousywater.R
import android.content.Intent
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.pjff.mousywater.databinding.ActivitySettingsBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.User
import com.pjff.mousywater.utils.Constants
import com.pjff.mousywater.utils.GlideLoader

//Bueno



/**
 * Setting screen of the app.
 */
// TODO Step 1: Replace the AppCompactActivity with BaseActivity.
class SettingsActivity : BaseActivity() {
    private lateinit var binding:ActivitySettingsBinding
    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO Step 3: Call the function to setup action bar.
        // START
        setupActionBar()
        // END
    }

    // TODO Step 11: Override the onResume function and call the getUserDetails function init.
    // START
    override fun onResume() {
        super.onResume()

        getUserDetails()
    }
    // END

    // TODO Step 2: Create a function to setup action bar.
    // START
    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(binding.toolbarSettingsActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        binding.toolbarSettingsActivity.setNavigationOnClickListener { onBackPressed() }
    }
    // END

    // TODO Step 4: Create a function to get the user details from firestore.
    // START
    /**
     * A function to get the user details from firestore.
     */
    private fun getUserDetails() {

        // Show the progress dialog
        showProgressDialog(resources.getString(R.string.please_wait))

        // Call the function of Firestore class to get the user details from firestore which is already created.
        FirestoreClass().getUserDetails(this@SettingsActivity)
    }
    // END

    // TODO Step 6: Create a function to receive the success result.
    // START
    /**
     * A function to receive the user details and populate it in the UI.
     */
    fun userDetailsSuccess(user: User) {

        // TODO Step 9: Set the user details to UI.
        // START
        // Hide the progress dialog
        hideProgressDialog()

        // Load the image using the Glide Loader class.
        GlideLoader(this@SettingsActivity).loadUserPicture(user.image,binding.ivUserPhoto )

        binding.tvName.text = "${user.firstName} ${user.lastName}"
        binding.tvGender.text = user.gender
        binding.tvEmail.text = user.email
        binding.tvMobileNumber.text = "${user.mobile}"
        // END
    }
    // END
}