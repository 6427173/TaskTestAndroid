package com.example.demo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.ItemCategoryBinding
import com.example.demo.databinding.ItemProductBinding
import com.example.demo.model.ProductModel
import com.example.demo.model.ProductType

class ProductInnerAdaptor(
    productList: List<ProductModel>,
    listener: ProductClickInterface
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var productList: MutableList<ProductModel>? = null
    private var listener: ProductClickInterface? = null

    init {
        this.listener = listener
        this.productList = productList as MutableList<ProductModel>?
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 0) {
            val binding = ItemCategoryBinding.inflate(inflater, parent, false)
            CategoryHolder(binding)
        } else {
            val binding = ItemProductBinding.inflate(inflater, parent, false)
            ProductHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (productList!![position].type == ProductType.Category) {
            0
        } else {
            1
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = productList!![position]
        if (holder is CategoryHolder) {
            holder.bindData(item)
        }
        else if(holder is ProductHolder) {
            holder.bindData(item)
        }
    }

    override fun getItemCount(): Int {
        return productList?.size ?: 0
    }

    inner class CategoryHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: ProductModel) {
            with(binding) {
                txtTitle.text = item.name
                imgFile.setImageResource(item.productImage)

                imgFile.setOnClickListener {
                    listener?.positionClicked(item.name)
                }
            }
        }
    }

    inner class ProductHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: ProductModel) {
            with(binding) {
                txtTitle.text = item.name
                imgFile.setImageResource(item.productImage)

                imgFile.setOnClickListener {
                    listener?.positionClicked(item.name)
                }
            }
        }
    }

    interface ProductClickInterface {
        fun positionClicked(name: String)
    }
}