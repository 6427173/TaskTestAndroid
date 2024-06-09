package com.spellchecker.speakandtranslate.adapters

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spellchecker.speakandtranslate.databinding.PhrasebookItemBinding
import com.spellchecker.speakandtranslate.prefrences.Prefrences
import com.spellchecker.speakandtranslate.ui.PhraseBookDetailActivity
import com.spellchecker.speakandtranslate.utils.ConstantsSpeak
import com.spellchecker.speakandtranslate.utils.LanguageSelection
import com.spellchecker.speakandtranslate.utils.LanguageSelection.LangFlags
import com.spellchecker.speakandtranslate.utils.LanguageSelection.LangName
import com.spellchecker.speakandtranslate.utils.LanguageSelection.phrasebookFlags
import com.spellchecker.speakandtranslate.utils.LanguageSelection.phraselangcode
import com.spellchecker.speakandtranslate.utils.SpeechText
import java.util.*

class PhrasebookDetailAdapter(

    phrase1:  List<String>,
    phrase2: List<String>,
    code: String

    ) : RecyclerView.Adapter<PhrasebookDetailAdapter.PhraseBookViewHolder>(), SpeechText.OnCompleteVoice {
    private val phrase1: List<String>
    private val phrase2: List<String>
    private val languageCode: String
    lateinit var prefsdata: Prefrences
    private var context: Context?=null
    var constants: ConstantsSpeak?=null
    var textToSpeech: TextToSpeech? = null
    inner class PhraseBookViewHolder(val binding: PhrasebookItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhraseBookViewHolder {
        val binding = PhrasebookItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        context=parent.context
        prefsdata= Prefrences(context!!)
        return PhraseBookViewHolder(binding)
    }
    // total number of rows
    override fun getItemCount(): Int {
        return phrase1.size
    }

    companion object {
        private const val TAG = "Phrase_Adapter"
    }

    // data is passed into the constructor
    init {
        this.phrase1 = phrase1
        this.phrase2 = phrase2
        languageCode = code
    }

    override fun onBindViewHolder(holder: PhraseBookViewHolder, position: Int) {
        with(holder){
            with(position){

                binding.inputlangaugetext.text=prefsdata.phrasebookinputdata
                binding.outputlangaugetext.text=prefsdata.phrasebookonputdata
              binding .flaginput.setImageResource(phrasebookFlags[prefsdata.initfromlang])
              binding .flagoutput.setImageResource(phrasebookFlags[prefsdata.inittolang])
                binding.inputtext.text = phrase1[position]
                binding.outtxt.text = phrase2[position]
                binding.ivcopy.setOnClickListener {
                    LanguageSelection.setClipboard(context!!, binding.outtxt.text.toString())
                }
                binding.ivshare.setOnClickListener {
                    LanguageSelection.shareText(context, binding.outtxt.text.toString())
                }
                binding.ivspeak.setOnClickListener {
                    speakOut(
                        binding.outtxt.text.toString(),
                        phraselangcode[prefsdata.inittolang], binding.outtxt.text.toString()
                    )
                }
            }
        }

    }
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