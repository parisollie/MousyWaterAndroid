package com.pjff.mousywater.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.FragmentDashboardBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.Product
import com.pjff.mousywater.ui.activities.SettingsActivity
import com.pjff.mousywater.ui.adapters.DashboardItemsListAdapter


class DashboardFragment : BaseFragment() {

    private var _binding:FragmentDashboardBinding?= null

    private val binding get () = _binding !!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDashboardBinding.inflate(inflater,container,false)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

            R.id.action_settings -> {

                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        getDashboardItemsList()
    }

    /**
     * A function to get the dashboard items list from cloud firestore.
     */
    private fun getDashboardItemsList() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getDashboardItemsList(this@DashboardFragment)
    }

    /**
     * A function to get the success result of the dashboard items from cloud firestore.
     *
     * @param dashboardItemsList
     */
    fun successDashboardItemsList(dashboardItemsList: ArrayList<Product>) {

        // Hide the progress dialog.
        hideProgressDialog()

        if (dashboardItemsList.size > 0) {

            binding.rvDashboardItems.visibility = View.VISIBLE
            binding.tvNoDashboardItemsFound.visibility = View.GONE

            binding.rvDashboardItems.layoutManager = GridLayoutManager(activity, 2)
            binding.rvDashboardItems.setHasFixedSize(true)

            val adapter = DashboardItemsListAdapter(requireActivity(), dashboardItemsList ,this@DashboardFragment)
            binding.rvDashboardItems.adapter = adapter
        } else {
            binding.rvDashboardItems.visibility = View.GONE
            binding.tvNoDashboardItemsFound.visibility = View.VISIBLE
        }
    }//end

}








