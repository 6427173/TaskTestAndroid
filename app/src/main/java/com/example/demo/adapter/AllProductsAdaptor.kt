package com.example.demo.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.ItemAllProductsBinding
import com.example.demo.model.AllProductModel


class AllProductsAdaptor(
    private val contexts: Activity,
    allProductList: List<AllProductModel>,
    listener: AllProductInterface
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var allProductList: MutableList<AllProductModel>? = null
    private var listener: AllProductInterface

    init {
        this.allProductList = allProductList as MutableList<AllProductModel>?
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAllProductsBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = allProductList!![position]
        if (holder is MyViewHolder) {
            holder.bindData(item, position)
        }
    }

    override fun getItemCount(): Int {
        return allProductList?.size ?: 0
    }

    inner class MyViewHolder(private val binding: ItemAllProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: AllProductModel, position: Int) {
            val adaptor = ProductInnerAdaptor(item.productList, object :
                ProductInnerAdaptor.ProductClickInterface {
                override fun positionClicked(name: String) {
                    listener.onProductClick(position)
                }

            })
            with(binding) {
                txtTitle.text = item.name
                if (position == 0)
                    recyclerInnerProducts.layoutManager =
                        GridLayoutManager(contexts, 3)
                else
                    recyclerInnerProducts.layoutManager =
                        LinearLayoutManager(contexts, RecyclerView.HORIZONTAL, false)
                recyclerInnerProducts.adapter = adaptor
            }

        }
    }

    interface AllProductInterface {
        fun onProductClick(position: Int)
    }
}