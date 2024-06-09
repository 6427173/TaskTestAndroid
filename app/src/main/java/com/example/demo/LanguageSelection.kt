package com.spellchecker.speakandtranslate.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.spellchecker.speakandtranslate.R
import com.spellchecker.speakandtranslate.adapters.LangAdapter
import com.spellchecker.speakandtranslate.databinding.BottomDialogBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import com.spellchecker.speakandtranslate.interfaces.LangSelection
import com.spellchecker.speakandtranslate.prefrences.Prefrences


object LanguageSelection {

    var LangName = arrayOf(
        "Afrikaans",
        "Amharic",
        "Arabic",
        "Armenian",
        "Azerbaycan",
        "Basque",
        "Bangla",
        "Bulgarian",
        "Catalan",
        "Croatian",
        "Czech",
        "Chinese",
        "Danish",
        "Dutch",
        "English",
        "Esperanto",
        "Estonian",
        "Filipino",
        "French",
        "Finnish",
        "Galician",
        "Georgian",
        "Gujarati",
        "German",
        "Greek",
        "Hebrew",
        "Hindi",
        "Hungarian",
        "Indonesian",
        "Icelandic",
        "Italian",
        "Javanese",
        "Japanese",
        "Kannada",
        "Khmer",
        "Korean",
        "Latin",
        "Latvian",
        "Lao",
        "Irish",
        "Lithuanian",
        "Malay",
        "Malayalam",
        "Moldavian",
        "Marathi",
        "Nepali",
        "Norwegian",
        "Persian",
        "Punjabi",
        "Pashto",
        "Polish",
        "Portuguese",
        "Romanian",
        "Russian",
        "Sinhala",
        "Slovak",
        "Slovenian",
        "Spanish",
        "Sundanese",
        "Swahili",
        "Swedish",
        "Serbian",
        "Tamil",
        "Telugu",
        "Thai",
        "Turkish",
        "Ukrainian",
        "Urdu",
        "Vietnamese",
        "Zulu"
    )

    var LangCode = arrayOf(
        "af-ZA",
        "am-ET",
        "ar-SA",
        "hy-AM",
        "az-AZ",
        "eu-ES",
        "bn-BD",
        "bg-BG",
        "ca-ES",
        "hr-HR",
        "cs-CZ",
        "zh",
        "da-DK",
        "nl-NL",
        "en-GB",
        "eo",
        "et",
        "fil-PH",
        "fr-FR",
        "fi-FI",
        "gl-ES",
        "ka-GE",
        "gu-IN",
        "de-DE",
        "el-GR",
        "he-IL",
        "hi-IN",
        "hu-HU",
        "id-ID",
        "is-IS",
        "it-IT",
        "jv-ID",
        "ja-JP",
        "kn-IN",
        "km-KH",
        "ko-KR",
        "la",
        "lv-LV",
        "lo-LA",
        "ga",
        "lt-LT",
        "ms-MY",
        "ml-IN",
        "mo",
        "mr-IN",
        "ne-NP",
        "nb-NO",
        "fa-IR",
        "pa",
        "ps",
        "pl-PL",
        "pt-PT",
        "ro-RO",
        "ru-RU",
        "si-LK",
        "sk-SK",
        "sl-SI",
        "es-ES",
        "su-ID",
        "sw-TZ",
        "sv-SE",
        "sr-RS",
        "ta-IN",
        "te-IN",
        "th-TH",
        "tr-TR",
        "uk-UA",
        "ur-PK",
        "vi-VN",
        "zu-ZA"
    )

    val phrasebookFlags= intArrayOf(
        R.drawable.arabic,
        R.drawable.dutch,
        R.drawable.english,
        R.drawable.french,
        R.drawable.german,
        R.drawable.hindi,
        R.drawable.spanish)


    val phraselangcode= arrayOf(
        "ar-SA",
        "eo",
        "en-GB",
        "fi-FI",
        "el-GR",
        "hi-IN",
        "es-ES")

    val LangFlags= intArrayOf(
        R.drawable.albanian,
        R.drawable.amharic,
        R.drawable.arabic,
        R.drawable.armenian,
        R.drawable.azerbaycan,
        R.drawable.basque,
        R.drawable.bengali,
        R.drawable.bulgarian,
        R.drawable.catalan,
        R.drawable.catalan,
        R.drawable.croatian,
        R.drawable.croatian,
        R.drawable.czech,
        R.drawable.chinese,
        R.drawable.english,
        R.drawable.dutch,
        R.drawable.english,
        R.drawable.english,
        R.drawable.filipino,
        R.drawable.french,
        R.drawable.finnish,
        R.drawable.galician,
        R.drawable.georgian,
        R.drawable.gujarati,
        R.drawable.german,
        R.drawable.greek,
        R.drawable.hebrew,
        R.drawable.hindi,
        R.drawable.hungarian,
        R.drawable.indonesian,
        R.drawable.icelandic,
        R.drawable.italy,
        R.drawable.javanese,
        R.drawable.japan,
        R.drawable.kannada,
        R.drawable.khmer,
        R.drawable.korean,
        R.drawable.latvian,
        R.drawable.lao,
        R.drawable.irish,
        R.drawable.lithuanian,
        R.drawable.malay,
        R.drawable.malayalam,
        R.drawable.moldavian,
        R.drawable.marathi,
        R.drawable.nepali,
        R.drawable.norwegian,
        R.drawable.persian,
        R.drawable.punjabi,
        R.drawable.pashto,
        R.drawable.polish,
        R.drawable.portuguese,
        R.drawable.romanian,
        R.drawable.russian,
        R.drawable.sinhala,
        R.drawable.slovak,
        R.drawable.slovenian,
        R.drawable.spanish,
        R.drawable.sundanese,
        R.drawable.swahili,
        R.drawable.swedish,
        R.drawable.serbian,
        R.drawable.tamil,
        R.drawable.telugu,
        R.drawable.thai,
        R.drawable.turkish,
        R.drawable.ukrainian,
        R.drawable.urdu,
        R.drawable.vietnamese,
        R.drawable.afrikaans
    )



    fun setClipboard(context: Context, text: String) {
        if (text.isEmpty()) {
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.text = text
                Toast.makeText(context, "Copied Text", Toast.LENGTH_SHORT).show()
            } else {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = ClipData.newPlainText("Copied Text", text)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context, "Copied Text", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun shareText(contxt: Context?, text: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        contxt?.startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    fun animatedView(view: View){
        val cx: Int =   view.getWidth() / 2
        val cy: Int =   view.getHeight() / 2

        // get the final radius for the clipping circle

        // get the final radius for the clipping circle
        val finalRadius: Int = Math.max(  view.getWidth(),  view.getHeight())

        // create the animator for this view (the start radius is zero)

        // create the animator for this view (the start radius is zero)
        val anim =
            ViewAnimationUtils.createCircularReveal( view, cx, cy, 0f, finalRadius.toFloat())

        // make the view visible and start the animation

        // make the view visible and start the animation
        view.setVisibility(  View.VISIBLE)
        anim.start()
    }

    fun bottomsheetDialog(context: Context, titletxt: String, typecheck: Int, langsele: LangSelection){
        lateinit var prefsdata: Prefrences
        prefsdata= Prefrences(context)
        val bottomdialogbinding: BottomDialogBinding
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
        bottomdialogbinding= BottomDialogBinding.inflate(inflater as LayoutInflater)
        val bottomSheetDialog = BottomSheetDialog(context,R.style.SheetDialog)
        bottomSheetDialog.setContentView(bottomdialogbinding.root)
        bottomdialogbinding.tittle.setText(titletxt)
        bottomSheetDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bottomdialogbinding.rvCounties.apply {
            layoutManager= LinearLayoutManager(context)
            var langselectadapter: LangAdapter
            langselectadapter= LangAdapter( LangName)
            bottomdialogbinding.rvCounties.adapter = langselectadapter
            langselectadapter.onItemClick = { itemposition ->
                if (typecheck == 1) {

                    langsele.inputpos(itemposition)
//                    mainfragmbinding.leftlabel.text = LanguageSelection.LangName[itemposition]
                    prefsdata!!.InputLangPos=itemposition

                } else {
                    langsele.outputpos(itemposition)
//                    mainfragmbinding.rightlabel.text = LanguageSelection.LangName[itemposition]
                    prefsdata!!.OutputLangPos=itemposition
                }

                bottomSheetDialog.dismiss()
            }
        }

        bottomSheetDialog.show()
    }

    fun isInternetOn(con:Context): Boolean {
        val mgr = con.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val netInfo = mgr!!.activeNetworkInfo
        return netInfo != null && netInfo.isConnected && netInfo.isAvailable
    }

    var settingsimagesview= arrayOf(
        R.drawable.ic_rate_us,
        R.drawable.ic_share,
    )
    var settingsapplabel = arrayOf(
        "Rate us",
        "Share",

)

}