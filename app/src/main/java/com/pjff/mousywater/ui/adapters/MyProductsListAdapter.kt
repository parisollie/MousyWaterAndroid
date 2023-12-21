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
import com.pjff.mousywater.utils.GlideLoader



open class MyProductsListAdapter(
   // private val binding: ItemListLayoutBinding,
    private val context: Context,
    private var list: ArrayList<Product>,
    private val fragment: ProductsFragment

    ): RecyclerView.Adapter<MyProductsListAdapter.ViewHolder>() {

    class ViewHolder( val binding: ItemListLayoutBinding): RecyclerView.ViewHolder(binding.root){

        val iv_item_image = binding.ivItemImage


        fun bind(product: Product){
            with(binding){
                //estos son nuestros textview
                tvItemPrice.text = product.price

                binding.apply {
                    //tvTitle , es como se llaman nuestras etiquetas
                    tvItemPrice.text = product.price
                    tvItemName.text = product.title

                }
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
        val model = list[position]

        //GlideLoader(context).loadProductPicture(model.image, binding.ivItemImage)












    }

}


