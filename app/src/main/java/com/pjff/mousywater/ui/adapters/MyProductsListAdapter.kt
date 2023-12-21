package com.pjff.mousywater.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pjff.mousywater.databinding.ItemListLayoutBinding
import com.pjff.mousywater.models.Product
import android.content.Context
import com.bumptech.glide.Glide
import com.pjff.mousywater.R
import com.pjff.mousywater.ui.fragments.ProductsFragment


open class MyProductsListAdapter(

    private val context: Context,
    private var list: ArrayList<Product>,
    private val fragment: ProductsFragment

    ): RecyclerView.Adapter<MyProductsListAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemListLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            with(binding){
                //estos son nuestros textview
                tvItemPrice.text = product.price
                val iv_item_image = binding.ivItemImage


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])

    }

}


