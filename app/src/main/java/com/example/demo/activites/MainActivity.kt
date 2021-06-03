package com.example.demo.activites

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo.R
import com.example.demo.adapter.AllProductsAdaptor
import com.example.demo.databinding.ActivityMainBinding
import com.example.demo.model.AllProductModel
import com.example.demo.model.ProductModel
import com.example.demo.model.ProductType

class MainActivity : AppCompatActivity(), AllProductsAdaptor.AllProductInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var allProductsAdaptor: AllProductsAdaptor
    private val allProductList: ArrayList<AllProductModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initList()
        initRecycler()
        setUpSpinnerAdapter()

    }

    private fun setUpSpinnerAdapter() {
        val spinnerArray: ArrayList<String> = arrayListOf()
        spinnerArray.add("Gurgaon")
        spinnerArray.add("Gurgaon")

        val spinnerArrayAdapter: ArrayAdapter<String> =
            ArrayAdapter(
                this,
                R.layout.spinner_item,
                spinnerArray
            )
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(binding) {
            spnFilter.adapter = spinnerArrayAdapter
            imgDropdown.setOnClickListener {
                spnFilter.performClick()
            }
        }
    }

    private fun initList() {

        val productList: ArrayList<ProductModel> = arrayListOf()
        productList.add(
            ProductModel(
                0, "Bed Room", "$100",
                R.drawable.ic_baseline_bed_24, ProductType.Category
            )
        )
        productList.add(
            ProductModel(
                1, "Meeting Room", "$100",
                R.drawable.ic_baseline_meeting_room_24, ProductType.Category
            )
        )
        productList.add(
            ProductModel(
                2, "Camera", "$100",
                R.drawable.ic_baseline_camera, ProductType.Category
            )
        )
        productList.add(
            ProductModel(
                3, "Setting", "$100",
                R.drawable.ic_baseline_settings, ProductType.Category
            )
        )
        productList.add(
            ProductModel(
                4, "AirPlane", "$100",
                R.drawable.ic_baseline_airplanemode, ProductType.Category
            )
        )
        productList.add(
            ProductModel(
                5, "Food", "$100",
                R.drawable.ic_baseline_fastfood_24, ProductType.Category
            )
        )


        val productListNew: ArrayList<ProductModel> = arrayListOf()
        productListNew.add(
            ProductModel(
                0, "Product 0", "$100",
                R.drawable.ic_baseline_bed_24, ProductType.Product
            )
        )
        productListNew.add(
            ProductModel(
                1, "Product 1", "$100",
                R.drawable.ic_baseline_meeting_room_24, ProductType.Product
            )
        )
        productListNew.add(
            ProductModel(
                2, "Product 2", "$100",
                R.drawable.ic_baseline_camera, ProductType.Product
            )
        )
        productListNew.add(
            ProductModel(
                3, "Product 3", "$100",
                R.drawable.ic_baseline_settings, ProductType.Product
            )
        )
        productListNew.add(
            ProductModel(
                4, "Product 4", "$100",
                R.drawable.ic_baseline_airplanemode, ProductType.Product
            )
        )
        productListNew.add(
            ProductModel(
                5, "Product 5", "$100",
                R.drawable.ic_baseline_fastfood_24, ProductType.Product
            )
        )


        allProductList.add(AllProductModel(0, "Our Categories", productList))
        allProductList.add(AllProductModel(1, "Trending Products", productListNew))
        allProductList.add(AllProductModel(2, "Recently Viewed Products", productListNew))


    }

    private fun initRecycler() {
        allProductsAdaptor = AllProductsAdaptor(this, allProductList, this)
        with(binding) {
            recyclerAllProduct.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerAllProduct.adapter = allProductsAdaptor
        }
    }

    override fun onProductClick(position: Int) {
        val intent = Intent(this, ProductDetailActivity::class.java)
        startActivity(intent)
    }
}