package com.spellchecker.speakandtranslate.fragments

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.spellchecker.speakandtranslate.R
import com.spellchecker.speakandtranslate.adapters.VoiceTranslateAdapter
import com.spellchecker.speakandtranslate.ads.InterstialAdCall
import com.spellchecker.speakandtranslate.database.VoiceRepository
import com.spellchecker.speakandtranslate.database.VoiceViewModelFactry
import com.spellchecker.speakandtranslate.database.VoicetransDatabase
import com.spellchecker.speakandtranslate.database.Voicetranslation
import com.spellchecker.speakandtranslate.databinding.FragmentMainBinding
import com.spellchecker.speakandtranslate.interfaces.LangSelection
import com.spellchecker.speakandtranslate.prefrences.Prefrences
import com.spellchecker.speakandtranslate.utils.CoroutineRunningTask
import com.spellchecker.speakandtranslate.utils.LanguageSelection
import com.spellchecker.speakandtranslate.utils.LanguageSelection.bottomsheetDialog
import com.spellchecker.speakandtranslate.viewmodels.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.json.JSONArray
import java.util.*



class MainFragment : Fragment(),View.OnClickListener, LangSelection {

lateinit var mainfragmbinding:FragmentMainBinding
    var lang:LangSelection?=null
 lateinit var mainviewmdel: SharedViewModel
    var inputtype:Int=1
    private val REQCODEINPUT = 110
    lateinit var prefsdata:Prefrences
    var inputlangcode: String? =null
    var outputlangcode: String? =null
    private  var mcontext: Context?=null
     var htttpclclient = OkHttpClient()
    lateinit var translatarotadaptr: VoiceTranslateAdapter
    private var adcounter:Int=0
    lateinit var voicelistdata:LiveData<List<Voicetranslation>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainfragmbinding=FragmentMainBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return mainfragmbinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefsdata= Prefrences(requireContext())
        initUi()
        lang=this
        bounceAnimation()
        val dao= VoicetransDatabase.getDatabase(mcontext!!.applicationContext).voiceTanslationDao()
        val repositry=VoiceRepository(dao)
       mainviewmdel=ViewModelProvider(this,VoiceViewModelFactry(repositry)).get(SharedViewModel::class.java)
        observerDataAdapter()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mcontext=context
    }
    private fun initUi() {

        mainfragmbinding.leftlabel.text = LanguageSelection.LangName[prefsdata.InputLangPos]
        mainfragmbinding.rightlabel.text = LanguageSelection.LangName[prefsdata.OutputLangPos]
        mainfragmbinding.leftmic.setOnClickListener(this)
        mainfragmbinding.rightmic.setOnClickListener(this)
        mainfragmbinding.leftlabel.setOnClickListener(this)
        mainfragmbinding.rightlabel.setOnClickListener(this)
        mainfragmbinding.ivswitch.setOnClickListener(this)
        mainfragmbinding.progressbar.visibility=View.VISIBLE
    }

    fun voiceInputPrompt(lanpos: Int){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        inputtype=lanpos

        if (lanpos == 1)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, LanguageSelection.LangCode[prefsdata.InputLangPos])
        else
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, LanguageSelection.LangCode[prefsdata.OutputLangPos])
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.app_name)
        )
        try {
            launchActivity.launch(intent)
           // startActivityForResult(intent, REQCODEINPUT)
        } catch (a: ActivityNotFoundException) {

        }
    }
    var launchActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val result = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            CoroutineScope(Dispatchers.IO).launch {
                voiceinputCall(result!![0])


            }
        }
    }

    private fun voiceinputCall(word: String) {
        if(inputtype==1) {
            inputlangcode = LanguageSelection.LangCode.get(prefsdata.InputLangPos)
            outputlangcode=LanguageSelection.LangCode.get(prefsdata.OutputLangPos)
        }
        else{
            inputlangcode = LanguageSelection.LangCode.get(prefsdata.OutputLangPos)
            outputlangcode=LanguageSelection.LangCode.get(prefsdata.InputLangPos)
        }

        val url = "https://translate.googleapis.com/translate_a/single?client=gtx&" +
                "sl=" + inputlangcode + "&" +
                "tl=" + outputlangcode +
                "&dt=t&q=" + word.trim { it <= ' ' }.replace(" ", "%20") + "&ie=UTF-8&oe=UTF-8"

        outpuResult(url,word)

    }
    override fun onClick(v: View?) {
     when(v?.id){
         R.id.leftmic->
             voiceInputPrompt(1)
         R.id.rightmic->
             voiceInputPrompt(2)
         R.id.leftlabel->
             bottomsheetDialog(mcontext!!, getString(R.string.translatefrom), 1, lang!!)
         R.id.rightlabel->
             bottomsheetDialog(mcontext!!, getString(R.string.translateintto), 2, lang!!)
         R.id.ivswitch->
             switchlangauges()

     }
    }
     fun outpuResult(url: String,wordinput:String)
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
                    Toast.makeText(mcontext, "There seems to be network issue", Toast.LENGTH_SHORT).show()

                }
                else
                {

                    if (inputtype==1){
                        val voiceData = Voicetranslation(0,
                            wordinput,
                            data,
                            prefsdata.InputLangPos,
                            prefsdata.OutputLangPos,
                            1
                        )
                        mainviewmdel.insertVoiceTranslation(voiceData)

                    }
                    else{

                            val voiceData = Voicetranslation(
                                0,
                                wordinput,
                                data,
                                prefsdata.OutputLangPos,
                                prefsdata.InputLangPos,
                                2,

                                )
                        mainviewmdel.insertVoiceTranslation(voiceData)

                    }
                    observerDataAdapter()
                    adcounter++
                    showinterstialtAd()
                }

            }
        })
    }

    private fun switchlangauges(){
        var temp=mainfragmbinding.leftlabel.text.toString()
        mainfragmbinding.leftlabel.text=mainfragmbinding.rightlabel.text
        mainfragmbinding.rightlabel.text= temp
        var inpulantemp=prefsdata!!.InputLangPos
        prefsdata!!.InputLangPos=prefsdata!!.OutputLangPos
        prefsdata!!.OutputLangPos= inpulantemp
    }
fun observerDataAdapter(){
    mainviewmdel.getvoicetranslation().observe(requireActivity(), Observer {
        mainfragmbinding.progressbar.visibility=View.GONE

if (it.size<=0){
    mainfragmbinding.recyclerView.visibility=View.GONE
    mainfragmbinding.emptyview.setText("");
    mainfragmbinding.emptyview.setCharacterDelay(100)
    mainfragmbinding.emptyview.animateText(mcontext!!.getString(R.string.Nodatafound))
   mainfragmbinding.emptyview.visibility=View.VISIBLE

}

        else{
    mainfragmbinding.recyclerView.visibility=View.VISIBLE
    mainfragmbinding.emptyview.visibility=View.GONE
        }
        mainfragmbinding.recyclerView.apply {
            layoutManager=LinearLayoutManager(mcontext)
            translatarotadaptr=VoiceTranslateAdapter(it,mainviewmdel)
            mainfragmbinding.recyclerView.adapter=translatarotadaptr
        }
    })

}
    fun bounceAnimation(){
        val animY = ObjectAnimator.ofFloat(mainfragmbinding.constraintLayout, "translationY", -60f, 0f)
        animY.duration = 800 //1sec

        animY.interpolator = BounceInterpolator()
        animY.repeatCount = 0
        animY.start()
    }

    override fun inputpos(pos: Int) {
        mainfragmbinding.leftlabel.text = LanguageSelection.LangName[pos]
    }

    override fun outputpos(pos: Int) {
        mainfragmbinding.rightlabel.text = LanguageSelection.LangName[pos]
    }


fun showinterstialtAd(){

    if (adcounter%2!=0){
        InterstialAdCall.getInstance().intertialsadaShow(requireActivity())
    }


}
}