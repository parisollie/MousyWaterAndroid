package com.pjff.mousywater.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pjff.mousywater.R
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.pjff.mousywater.databinding.ActivityMainBinding
import com.pjff.mousywater.databinding.ActivityUserProfileBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.User
import com.pjff.mousywater.utils.Constants

import java.io.IOException

// TODO Step 1: Create an activity as UserProfileActivity.
// START

/**
 * A user profile screen.
 */
class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding:ActivityUserProfileBinding
    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // TODO Step 5: Retrieve the User details from intent extra.
        // START
        // Create a instance of the User model class.
        var userDetails: User = User()
        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            // Get the user details from intent as a ParcelableExtra.
            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }
        // END

        // TODO Step 6: After receiving the user details from intent set it to the UI.

        // Here, the some of the edittext components are disabled because it is added at a time of Registration.
        binding.etFirstName.isEnabled = false
        binding.etFirstName.setText(userDetails.firstName)

        binding.etLastName.isEnabled = false
        binding.etLastName.setText(userDetails.lastName)

        binding.etEmail.isEnabled = false
        binding.etEmail.setText(userDetails.email)
        // END
    }
}