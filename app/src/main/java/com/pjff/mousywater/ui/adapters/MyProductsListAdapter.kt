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

        val ivItemImage = binding.ivItemImage
        fun bind(product: Product){
            with(binding){
                //estos son nuestros textview


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


        //val song = songs[position]

        //holder.bind(song)

        var image: ByteArray? = null



        //Sino tengo imagen la importo
        if(image != null){
            GlideLoader(context).loadProductPicture(model.image, holder.ivItemImage)
            /*Glide.with(holder.itemView.context).asBitmap()
                .load(image)
                .into(holder.ivItemImage)*/
        }else{
            holder.ivItemImage.setImageResource(R.drawable.ic_vector_edit)
        }















    }

}


