package com.spellchecker.speakandtranslate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spellchecker.speakandtranslate.databinding.ItemseetingsviewBinding
import com.spellchecker.speakandtranslate.ui.MainActivity
import com.spellchecker.speakandtranslate.utils.LanguageSelection
import com.spellchecker.speakandtranslate.utils.LanguageSelection.animatedView

class SettingViewAdapter:RecyclerView.Adapter<SettingViewAdapter.MainViewHolder>() {


    var mcontxt: Context?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        val mainviewbinding=ItemseetingsviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        mcontxt=parent.context
        return MainViewHolder(mainviewbinding)
    }


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        with(holder) {
            mainsettingbinding.ivicons.setImageResource(LanguageSelection.settingsimagesview[position])
            mainsettingbinding.txtlabel.text=LanguageSelection.settingsapplabel[position]

            itemView.setOnClickListener {
                animatedView(itemView)
                when(position){
                    0->{
                        (mcontxt as MainActivity).rateUS()
                    }
                    1->{
                        (mcontxt as MainActivity).shareApplication()
                    }

                }

            }

        }
    }


    override fun getItemCount()=LanguageSelection.settingsimagesview.size

    inner class MainViewHolder(val mainsettingbinding: ItemseetingsviewBinding) : RecyclerView.ViewHolder(mainsettingbinding.root)
}