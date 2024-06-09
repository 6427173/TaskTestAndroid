package com.spellchecker.speakandtranslate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spellchecker.speakandtranslate.R
import com.spellchecker.speakandtranslate.databinding.DicadapterBinding
import com.spellchecker.speakandtranslate.dictionary.Meanings
import com.spellchecker.speakandtranslate.utils.ConstantsSpeakDic
import com.spellchecker.speakandtranslate.utils.LanguageSelection.shareText

class DicAdapter (val datalist: List<Meanings>?, val mContext: Context, val searchString: String, val listener: ClickListener) : RecyclerView.Adapter<DicAdapter.DicViewHolder>(){
    var clicklistner: ClickListener =this.listener
    private val speaker = ConstantsSpeakDic()

    init
    {

        speaker.ttsInitialization(mContext)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DicAdapter.DicViewHolder {
        val itemBinding = DicadapterBinding.inflate(LayoutInflater.from(mContext), parent, false)

        return DicViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: DicViewHolder, positoion: Int) {
        val dictionaryModel = datalist?.get(positoion)

        dictionaryModel?.let {
            holder.bindData(it)
            holder.setClickListeners(it, positoion)
        }
    }

    override fun getItemCount()= datalist?.size ?: 0
    interface ClickListener {
        fun onClicksListener()
    }


    inner class DicViewHolder(val itemBinding: DicadapterBinding) : RecyclerView.ViewHolder(itemBinding.root)
    {

        fun bindData(dictionaryMainModel: Meanings)
        {

            var synm_flag = true
            itemBinding.apply {
                definition.text = ""
                example.text = ""
                leftSyno.text = ""
                rightSyno.text = ""
                partOfSpeech.text = dictionaryMainModel.partOfSpeech
            }

            dictionaryMainModel.definationlist?.forEach {

                it.defination?.let {
                    itemBinding.definition.append(it + "\n")
                }
                it.examples?.let {
                    itemBinding.example.append(it + "\n")
                }


                it.synonyms?.forEach {
                    if (synm_flag)
                    {
                        itemBinding.leftSyno.append(it + "\n")
                        synm_flag = false
                    }
                    else
                    {
                        itemBinding.rightSyno.append(it + "\n")
                        synm_flag = true
                    }
                }
            }
        }


        fun setClickListeners(dictionaryMainModel: Meanings, position: Int)
        {
            itemBinding.apply {

                ivShareText.setOnClickListener {

                    var synm_flag = true

                    val definitions = StringBuilder()
                    val examples = StringBuilder()
                    val left_syno = StringBuilder()
                    val right_syno = StringBuilder()

                    dictionaryMainModel.definationlist?.forEach {


                        it.defination?.let {
                            definitions.append(it + "\n")
                        }

                        it.examples?.let {
                            examples.append(it + "\n")
                        }


                        it.synonyms?.forEach {
                            if (synm_flag)
                            {
                                left_syno.append(it + "\n")
                                synm_flag = false
                            }
                            else
                            {
                                right_syno.append(it + "\n")
                                synm_flag = true
                            }
                        }
                    }


                    val textToShare = "$searchString (${dictionaryMainModel.partOfSpeech}) \n \n" +
                            "${mContext.getString(R.string.defenation)}: \n${definitions} \n \n" +
                            "${mContext.getString(R.string.examples)}: \n${examples} \n \n" +
                            "${mContext.getString(R.string.synonyms)}: \n${left_syno} \n$right_syno"

                shareText(mContext,textToShare)

                }

                ivSpeakerHeading.setOnClickListener {
                    val text = itemBinding.definition.text.toString().replace("*", "").replace("^", "").replace("!", "").replace("~", "")
                  startSpeaking(text)
                }
                btnClose.setOnClickListener {
                  stopTTS()
                    clicklistner.onClicksListener()
                }

                ivSpeakerExample.setOnClickListener {
                    val text = itemBinding.example.text.toString().replace("*", "").replace("^", "").replace("!", "").replace("~", "")
                startSpeaking(text)
                }

                if (position == 0)
                {
                    ivSpeakerHeading.performClick()
                }
            }
        }
    }

    fun startSpeaking(text: String)
    {
        stopTTS()
        speaker.forSpeak(text, "en", mContext)



    }
    fun stopTTS()
    {
        speaker.getTts().stop()
        speaker.getTts().shutdown()
    }
}