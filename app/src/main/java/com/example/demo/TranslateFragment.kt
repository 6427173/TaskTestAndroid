package com.spellchecker.speakandtranslate.fragments

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.spellchecker.speakandtranslate.R
import com.spellchecker.speakandtranslate.ads.InterstialAdCall
import com.spellchecker.speakandtranslate.ads.NativeAdsInit
import com.spellchecker.speakandtranslate.databinding.FragmentTranslatBinding
import com.spellchecker.speakandtranslate.interfaces.LangSelection
import com.spellchecker.speakandtranslate.prefrences.Prefrences
import com.spellchecker.speakandtranslate.utils.ConstantsSpeak
import com.spellchecker.speakandtranslate.utils.CoroutineRunningTask
import com.spellchecker.speakandtranslate.utils.LanguageSelection
import com.spellchecker.speakandtranslate.utils.LanguageSelection.LangFlags
import com.spellchecker.speakandtranslate.utils.SpeechText
import okhttp3.OkHttpClient
import org.json.JSONArray
import java.util.*


class TranslatFragment : Fragment(),View.OnClickListener,LangSelection, SpeechText.OnCompleteVoice {

    var inputtype:Int=1
    var inputlangcode: String? =null
    var outputlangcode: String? =null
    var htttpclclient = OkHttpClient()
    lateinit var texttransbinding: FragmentTranslatBinding
    var lang:LangSelection?=null
    var constants: ConstantsSpeak?=null
    lateinit var prefsdata: Prefrences
    var textToSpeech: TextToSpeech? = null
    lateinit var tts: TextToSpeech
    lateinit var nativeadcall: NativeAdsInit
    private lateinit var mcontext: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        texttransbinding=FragmentTranslatBinding.inflate(inflater,container,false)
        lang=this
        prefsdata= Prefrences(requireContext())
        // Inflate the layout for this fragment
        return texttransbinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mcontext=requireContext()
        initUiViews()
        viewHide()
    }

    override fun onClick(v: View?) {
        when(v?.id){  R.id.leftlabel->
            LanguageSelection.bottomsheetDialog(
                requireContext(),
                getString(R.string.translatefrom),
                1,
                lang!!
            )

            R.id.rightlabel->
                LanguageSelection.bottomsheetDialog(
                    requireContext(),
                    getString(R.string.translateintto),
                    2,
                    lang!!
                )
            R.id.ivspeak->
                speakOut(texttransbinding.tvoutput.text.toString(),outputlangcode!!)
            R.id.ivdelete->
                deleteInput()
            R.id.ivshare->
                LanguageSelection.shareText(context, texttransbinding.tvoutput.text.toString())
            R.id.ivcopy->
                LanguageSelection.setClipboard(requireContext(), texttransbinding.tvoutput.text.toString())
            R.id.micinput->
                voiceInputPrompt()
            R.id.ivswitch->
                switchlangauges()
            R.id.translateevent->
                voiceinputCall(texttransbinding.etinput.text.toString())

        }
    }
    fun deleteInput(){
        texttransbinding.tvoutput.text.clear()
        texttransbinding.etinput.text.clear()
        texttransbinding.shimerlayoutsmall.shimerlayout.visibility=View.GONE
        texttransbinding.shimerlayoutparentview.shimerview.visibility=View.VISIBLE
        texttransbinding.adviewnative.visibility=View.GONE
        nativeAdCallInit()
        viewHide()
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun translateData(data: String) {
        constants= ConstantsSpeak(requireContext())
        constants?.forSpeakSaveInitialization(
            outputlangcode!!
        )
        constants?.forSpeakAndSaveSpeak(
            data,
            outputlangcode!!,
            texttransbinding.etinput.text.toString(),
            texttransbinding.tvoutput.text.toString(),
            this,
            false
        )
    }


    private fun nativeAdCallInit() {
        nativeadcall=NativeAdsInit()
        nativeadcall.loadNativeAd(requireActivity(),texttransbinding.shimerlayoutparentview.shimerview,texttransbinding.adviewnative,texttransbinding.nativeAd)
    }
    private fun nativeAdsmall() {
        nativeadcall=NativeAdsInit()
        nativeadcall.loadNativeAdSmal(requireActivity(),texttransbinding.shimerlayoutsmall.shimerlayout,texttransbinding.adviewnative,texttransbinding.nativeAd)
    }
    private fun speakOut(speaktext: String,outputlanguage:String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            translateData(speaktext)

        } else {

            textToSpeech(speaktext,outputlanguage)
        }
    }
    private fun textToSpeech(wordToSpeak: String,outputcode:String) {
        val map = HashMap<String, String>()
        map[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "MessageId"
        textToSpeech?.language = Locale(outputcode)
        textToSpeech?.speak(wordToSpeak, TextToSpeech.QUEUE_FLUSH, map)
    }
    private fun voiceinputCall(word: String) {
            LanguageSelection.animatedView(texttransbinding.translateevent)
            inputlangcode = LanguageSelection.LangCode.get(prefsdata.InputLangPos)
            outputlangcode=LanguageSelection.LangCode.get(prefsdata.OutputLangPos)

        val url = "https://translate.googleapis.com/translate_a/single?client=gtx&" +
                "sl=" + inputlangcode + "&" +
                "tl=" + outputlangcode +
                "&dt=t&q=" + word.trim { it <= ' ' }.replace(" ", "%20") + "&ie=UTF-8&oe=UTF-8"

        outpuResult(url)

    }

    fun initUiViews(){
        nativeAdCallInit()
        texttransbinding.tvoutput.keyListener=null
        texttransbinding.leftlabel.setOnClickListener(this)
        texttransbinding.micinput.setOnClickListener(this)
        texttransbinding.rightlabel.setOnClickListener(this)
        texttransbinding.translateevent.setOnClickListener(this)
        texttransbinding.ivswitch.setOnClickListener(this)
        texttransbinding.ivdelete.setOnClickListener(this)
        texttransbinding.ivshare.setOnClickListener(this)
        texttransbinding.ivcopy.setOnClickListener(this)
        texttransbinding.ivspeak.setOnClickListener(this)
        texttransbinding.leftlabel.text= LanguageSelection.LangName[prefsdata!!.InputLangPos]
        texttransbinding.rightlabel.text= LanguageSelection.LangName[prefsdata!!.OutputLangPos]
        texttransbinding.flagleft.setImageResource(LangFlags[prefsdata.InputLangPos])
        texttransbinding.flagright.setImageResource(LangFlags[prefsdata.OutputLangPos])
        tts = TextToSpeech(requireContext()) { status ->
            if (status != TextToSpeech.ERROR) {
                tts.language = Locale.ENGLISH
            }
        }
    }
    private fun switchlangauges(){
        var temp=texttransbinding.leftlabel.text.toString()
        texttransbinding.leftlabel.text=texttransbinding.rightlabel.text
        texttransbinding.rightlabel.text= temp
        var inpulantemp=prefsdata!!.InputLangPos
        prefsdata!!.InputLangPos=prefsdata!!.OutputLangPos
        prefsdata!!.OutputLangPos= inpulantemp
        texttransbinding.flagleft.setImageResource(LangFlags[prefsdata.InputLangPos])
        texttransbinding.flagright.setImageResource(LangFlags[prefsdata.OutputLangPos])
    }
    override fun inputpos(pos: Int) {
        texttransbinding.leftlabel.text = LanguageSelection.LangName[pos]
        texttransbinding.flagleft.setImageResource(LangFlags[pos])
    }

    override fun outputpos(pos: Int) {
        texttransbinding.rightlabel.text = LanguageSelection.LangName[pos]
        texttransbinding.flagright.setImageResource(LangFlags[pos])
    }
    fun outpuResult(url: String)
    {
        CoroutineRunningTask(requireContext()).run(object : CoroutineRunningTask.RunCoroutineTask
        {
            override fun onRun(): String
            {

                val builder: okhttp3.Request.Builder = okhttp3.Request.Builder()
                builder.url(url)
                val request: okhttp3.Request = builder.build()
                try
                {
                    val response = htttpclclient.newCall(request).execute()
                    val jsonArray = JSONArray(Objects.requireNonNull(response.body)?.string())
                    val jsonArray2: JSONArray = jsonArray.getJSONArray(0)
                    val jsonArray3: JSONArray = jsonArray2.getJSONArray(0)
                    var data = ""
                    data = data + jsonArray3.getString(0)
                    return data
                }
                catch (e: Exception)
                {
                    e.printStackTrace()
                }
                return ""
            }

            override fun onComplete(data: String)
            {

                if (data.isEmpty() || data.equals("null") || data.equals("Null"))
                {
                    Toast.makeText(requireContext(), "There seems to be network issue", Toast.LENGTH_SHORT).show()
                    texttransbinding.tvoutput.setText("", TextView.BufferType.SPANNABLE)
                }
                else
                {

                    viewVisible()
                    texttransbinding.shimerlayoutsmall.shimerlayout.visibility=View.VISIBLE
                    texttransbinding.shimerlayoutparentview.shimerview.visibility=View.GONE
                    texttransbinding.adviewnative.visibility=View.GONE
                    nativeAdsmall()
                    texttransbinding.tvoutput.setText(data, TextView.BufferType.SPANNABLE)
                    showInterstialAd()
                }

            }
        })
    }
fun viewVisible(){
    texttransbinding.ansview.visibility=View.VISIBLE
    texttransbinding.ivshare.visibility=View.VISIBLE
    texttransbinding.ivcopy.visibility=View.VISIBLE
    texttransbinding.ivspeak.visibility=View.VISIBLE
    texttransbinding.ivdelete.visibility=View.VISIBLE
    texttransbinding.tvoutput.visibility=View.VISIBLE
}

    fun viewHide(){
        texttransbinding.ansview.visibility=View.GONE
        texttransbinding.ivshare.visibility=View.GONE
        texttransbinding.ivcopy.visibility=View.GONE
        texttransbinding.ivspeak.visibility=View.GONE
        texttransbinding.ivdelete.visibility=View.GONE
        texttransbinding.tvoutput.visibility=View.GONE
    }
    fun voiceInputPrompt(){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, LanguageSelection.LangCode[prefsdata.InputLangPos])
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.app_name)
        )
        try {
            voiceresult.launch(intent)
            // startActivityForResult(intent, REQCODEINPUT)
        } catch (a: ActivityNotFoundException) {

        }
    }
    var voiceresult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val result = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                voiceinput(result!![0])
        }
    }
    private fun voiceinput(word: String) {
        texttransbinding.etinput.text=word.toEditable()
    }
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
    override fun onComplete() {

    }

    override fun onFail() {

    }


    private fun showInterstialAd(){

            InterstialAdCall.getInstance().intertialsadaShow(requireActivity())


    }
}