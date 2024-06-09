package com.spellchecker.speakandtranslate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spellchecker.speakandtranslate.databinding.LangItemsBinding
import com.spellchecker.speakandtranslate.utils.LanguageSelection

class LangAdapter(private val title: Array<String>): RecyclerView.Adapter<LangAdapter.CustomHolder>() {

    var onItemClick: ((Int) -> Unit)? = null
    inner class CustomHolder(val binding: LangItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LangAdapter.CustomHolder {
        val binding = LangItemsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        with(holder) {
            with(title[position]) {
                binding.flagsCountry.setImageResource(LanguageSelection.LangFlags[position])
                binding.tvCountry.text = title[position]

            }
        }
    }

    override fun getItemCount()=title.size
}