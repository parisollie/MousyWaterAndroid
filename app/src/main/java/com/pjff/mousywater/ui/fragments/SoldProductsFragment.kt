package com.pjff.mousywater.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pjff.mousywater.R
import com.pjff.mousywater.databinding.FragmentOrdersBinding
import com.pjff.mousywater.databinding.FragmentSoldBinding
import com.pjff.mousywater.firestore.FirestoreClass
import com.pjff.mousywater.models.Order
import com.pjff.mousywater.models.SoldProduct
import com.pjff.mousywater.ui.adapters.MyOrdersListAdapter
import com.pjff.mousywater.ui.adapters.SoldProductsListAdapter



/**
 * Sold Products Listing Fragment.
 */
class SoldProductsFragment : BaseFragment() {

    private var _binding: FragmentSoldBinding?= null

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
        _binding = FragmentSoldBinding.inflate(inflater,container,false)
        val root = inflater.inflate(R.layout.fragment_sold, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        getSoldProductsList()
    }

    private fun getSoldProductsList() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        // Call the function of Firestore class.
        FirestoreClass().getSoldProductsList(this@SoldProductsFragment)
    }

    /**
     * A function to get the list of sold products.
     */
    fun successSoldProductsList(soldProductsList: ArrayList<SoldProduct>) {

        // Hide Progress dialog.
        hideProgressDialog()

        if (soldProductsList.size > 0) {
            binding.rvSoldProductItems.visibility = View.VISIBLE
            binding.tvNoSoldProductsFound.visibility = View.GONE

            binding.rvSoldProductItems.layoutManager = LinearLayoutManager(activity)
            binding.rvSoldProductItems.setHasFixedSize(true)

            val soldProductsListAdapter =
                SoldProductsListAdapter(requireActivity(), soldProductsList)
            binding.rvSoldProductItems.adapter = soldProductsListAdapter
        } else {
            binding.rvSoldProductItems.visibility = View.GONE
            binding.tvNoSoldProductsFound.visibility = View.VISIBLE
        }
    }
}