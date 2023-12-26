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
import com.pjff.mousywater.ui.adapters.MyOrdersListAdapter

class SoldProductsFragment : BaseFragment()  {

    private var _binding: FragmentSoldBinding?= null

    private val binding get () = _binding !!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSoldBinding.inflate(inflater,container,false)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return binding.root

    }


}