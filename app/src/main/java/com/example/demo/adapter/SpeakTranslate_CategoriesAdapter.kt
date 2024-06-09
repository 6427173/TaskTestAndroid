package com.spellchecker.speakandtranslate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spellchecker.speakandtranslate.R
import com.spellchecker.speakandtranslate.interfaces.RecyclerViewClickListener

class SpeakTranslate_CategoriesAdapter (private val context: Context?, list: List<String>, StListener: RecyclerViewClickListener) :
    RecyclerView.Adapter<SpeakTranslate_CategoriesAdapter.ViewHolder>() {

    private val listdata: List<String>
    private val StInflater: LayoutInflater
    private val StListener: RecyclerViewClickListener

    var countItems = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = StInflater.inflate(R.layout.phrasecategories_item_st, parent, false)

        return ViewHolder(view, StListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(listdata[position]) {
                holder.textmainitem.text=listdata[position]
                when(position){
                    0->
                        holder.ImageIcon?.setBackgroundResource(R.drawable.ic_general)
                    1->
                        holder.ImageIcon?.setBackgroundResource(R.drawable.ic_travelling)
                    2->
                        holder.ImageIcon?.setBackgroundResource(R.drawable.ic_market)
                    3->
                        holder.ImageIcon?.setBackgroundResource(R.drawable.ic_meal_time)
                    4 ->
                    holder.ImageIcon?.setBackgroundResource(R.drawable.ic_date_time)
                    5->
                        holder.ImageIcon?.setBackgroundResource(R.drawable.ic_hospital)
                    6->
                        holder.ImageIcon?.setBackgroundResource(R.drawable.ic_technology)
                    7->
                        holder.ImageIcon?.setBackgroundResource(R.drawable.ic_airport)
                    8->
                        holder.ImageIcon?.setBackgroundResource(R.drawable.ic_emergency)
                    9->
                        holder.ImageIcon?.setBackgroundResource(R.drawable.ic_police)
                    10->
                        holder.ImageIcon?.setBackgroundResource(R.drawable.ic_public__ofc)
                    else->
                        holder.ImageIcon?.setBackgroundResource(R.drawable.ic_resturant)
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return listdata.size
    }
    inner class ViewHolder internal constructor(
        itemView: View,
        listener: RecyclerViewClickListener
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var textmainitem: TextView
        var ImageIcon: ImageView? = null
        private val StListener: RecyclerViewClickListener
        override fun onClick(v: View) {
            StListener.onItemClick(adapterPosition)
        }

        init {
            textmainitem = itemView.findViewById(R.id.st_phrase)
            ImageIcon = itemView.findViewById(R.id.stImg)
            StListener = listener
            itemView.setOnClickListener(this)
        }
    }

    // data is pass to constructor
    init {
        StInflater = LayoutInflater.from(context)
        this.listdata = list
        this.StListener = StListener
    }
}