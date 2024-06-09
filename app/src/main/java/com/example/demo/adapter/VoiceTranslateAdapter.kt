package com.spellchecker.speakandtranslate.adapters

import android.app.Activity
import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spellchecker.speakandtranslate.R
import com.spellchecker.speakandtranslate.ads.NativeAdsInit
import com.spellchecker.speakandtranslate.database.Voicetranslation
import com.spellchecker.speakandtranslate.databinding.VoicetranslateBinding
import com.spellchecker.speakandtranslate.utils.ConstantsSpeak
import com.spellchecker.speakandtranslate.utils.LanguageSelection
import com.spellchecker.speakandtranslate.utils.LanguageSelection.setClipboard
import com.spellchecker.speakandtranslate.utils.LanguageSelection.shareText
import com.spellchecker.speakandtranslate.utils.SpeechText
import com.spellchecker.speakandtranslate.viewmodels.SharedViewModel
import java.util.*

class VoiceTranslateAdapter(var listdata:List<Voicetranslation>,var mainviewmdel: SharedViewModel):RecyclerView.Adapter<VoiceTranslateAdapter.VoiceTranslateViewHolder>(),
    SpeechText.OnCompleteVoice {

    var constants: ConstantsSpeak?=null
    var textToSpeech: TextToSpeech? = null
    lateinit var context:Context
    lateinit var nativeadcall: NativeAdsInit

    inner class VoiceTranslateViewHolder(val binding: VoicetranslateBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoiceTranslateViewHolder {
        val binding = VoicetranslateBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        context=parent.context

        return VoiceTranslateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VoiceTranslateViewHolder, position: Int) {

with(holder)
{
    with(listdata[position]) {
        binding.inputtext.text = inputword
        binding.inputlangaugetext.text = LanguageSelection.LangName[inputlang]
        binding.outputlangaugetext.text = LanguageSelection.LangName[outputlang]
        binding.outtxt.text = outword
        binding.flaginput.setImageResource(LanguageSelection.LangFlags[inputlang])
        binding.flagoutput.setImageResource(LanguageSelection.LangFlags[outputlang])


        binding.ivcopy.setOnClickListener {
            setClipboard(context!!, binding.outtxt.text.toString())
        }
        binding.ivdelete.setOnClickListener {


            dataDelete(listdata[position])

        }
        binding.ivshare.setOnClickListener {
            shareText(context, binding.outtxt.text.toString())
        }
        binding.ivspeak.setOnClickListener {
            speakOut(
                binding.outtxt.text.toString(),
                LanguageSelection.LangCode[outputlang], binding.outtxt.text.toString()
            )
        }

        if (position==0){
            nativeAdCallInit(context,binding)
        }
        else{
            binding.shimerlayoutparentview.shimerlayout.visibility=View.GONE
            binding.adviewnative.visibility=View.GONE
        }
    }
}
    }
    private fun nativeAdCallInit(contxt:Context,bindings: VoicetranslateBinding) {
        nativeadcall=NativeAdsInit()
        nativeadcall.loadNativeAdSmal(context as Activity,bindings.shimerlayoutparentview.shimerlayout,bindings.adviewnative,bindings.nativeAd)
    }

    override fun getItemCount(): Int {
return listdata.size
    }
    fun dataDelete(voicelist:Voicetranslation) {
        mainviewmdel.deleteVoiceTranslation(voicelist)
        notifyDataSetChanged()
    }
    //SPEAK THE CURRENT INPUT FROM THE EDITTEXT VIEWER
    fun speakOut(speaktext: String,outputlanguage:String,inputdata:String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            translateData(speaktext,outputlanguage,inputdata)
        } else {
            textToSpeech(speaktext,outputlanguage)
        }
    }
    private fun translateData(data: String,outputlangcode:String,inputdata:String) {
        constants= context?.let { ConstantsSpeak(it) }
        constants?.forSpeakSaveInitialization(
            outputlangcode
        )

        constants?.forSpeakAndSaveSpeak(
            data,
            outputlangcode,
            inputdata,
            data,
            this,
            false
        )
    }
    private fun textToSpeech(wordToSpeak: String,outputcode:String) {
        val map = HashMap<String, String>()
        map[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "MessageId"
        textToSpeech?.language = Locale(outputcode)
        textToSpeech?.speak(wordToSpeak, TextToSpeech.QUEUE_FLUSH, map)
    }

    override fun onComplete() {

    }

    override fun onFail() {

    }
}