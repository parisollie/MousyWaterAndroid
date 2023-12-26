package com.pjff.mousywater.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.FragmentDashboardBinding
import com.pjff.mousywater.databinding.FragmentOrdersBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.Order
import com.pjff.mousywater.ui.adapters.MyOrdersListAdapter


class OrdersFragment : BaseFragment()  {

    private var _binding: FragmentOrdersBinding?= null

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

        _binding = FragmentOrdersBinding.inflate(inflater,container,false)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return binding.root

    }

    // TODO Step 6: Create a function to get the success result of the my order list from cloud firestore.
    // START
    /**
     * A function to get the success result of the my order list from cloud firestore.
     *
     * @param ordersList List of my orders.
     */
    fun populateOrdersListInUI(ordersList: ArrayList<Order>) {

        // Hide the progress dialog.
        hideProgressDialog()

        // TODO Step 11: Populate the orders list in the UI.
        // START
        if (ordersList.size > 0) {

            binding.rvMyOrderItems.visibility = View.VISIBLE
            binding.tvNoOrdersFound.visibility = View.GONE

            binding.rvMyOrderItems.layoutManager = LinearLayoutManager(activity)
            binding.rvMyOrderItems.setHasFixedSize(true)

            val myOrdersAdapter = MyOrdersListAdapter(requireActivity(), ordersList)
            binding.rvMyOrderItems.adapter = myOrdersAdapter
        } else {
            binding.rvMyOrderItems.visibility = View.GONE
            binding.tvNoOrdersFound.visibility = View.VISIBLE
        }
        // END
    }// END


    // TODO Step 8: Create a function to call the firestore class function to get the list of my orders.
    // START
    /**
     * A function to get the list of my orders.
     */
    private fun getMyOrdersList() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().getMyOrdersList(this@OrdersFragment)
    }
    // END


    // TODO Step 9: Override the on resume function and call the getMyOrdersList in it.
    // START

    override fun onResume() {
        super.onResume()

        getMyOrdersList()
    }
    // END
}