package com.spellchecker.speakandtranslate.prefrences

import android.content.Context
import android.content.SharedPreferences

class Prefrences(contxt:Context) {


    private var inputlanposition="Inputlanpos"
    private var outputlanposition="outputlanpos"
    private var isapprated="appratestatus"
    private var permissionallowed="permissionallow"
    private var intspinnerpositionfrom="fromlanguge"
    private var intspinnerpositionto="tolanguge"
    private var phraseinput="phrasebookinput"
    private var phraseoutput="phrasebookoutput"
    private var speedprogressbar="speedproprefs"
    private var pitchprogressbar="pitchproprefs"
    private var pitchpref="pitchselection"
    private val preferences: SharedPreferences = contxt.getSharedPreferences(Context.MODE_PRIVATE.toString(),Context.MODE_PRIVATE)


      var phrasebookinputdata:String
           get()= preferences.getString(phraseinput,"English")!!
           set(value)=preferences.edit().putString(phraseinput,value).apply()
    var phrasebookonputdata:String
        get()= preferences.getString(phraseoutput,"Arabic")!!
        set(value)=preferences.edit().putString(phraseoutput,value).apply()
    var initfromlang: Int
        get() = preferences.getInt(intspinnerpositionfrom, 3)
        set(value) = preferences.edit().putInt(intspinnerpositionfrom, value).apply()
    var inittolang: Int
        get() = preferences.getInt(intspinnerpositionto, 0)
        set(value) = preferences.edit().putInt(intspinnerpositionto, value).apply()

    var InputLangPos: Int
        get() = preferences.getInt(inputlanposition, 14)
        set(value) = preferences.edit().putInt(inputlanposition, value).apply()

    var OutputLangPos: Int
        get() = preferences.getInt(outputlanposition, 2)
        set(value) = preferences.edit().putInt(outputlanposition, value).apply()


    var speedprogress: Int
        get() = preferences.getInt(speedprogressbar,50)
        set(value) = preferences.edit().putInt(speedprogressbar, value).apply()
    var pitchprogress: Int
        get() = preferences.getInt(pitchprogressbar,50)
        set(value) = preferences.edit().putInt(pitchprogressbar, value).apply()
    var speedselection: Float
        get() = preferences.getFloat(pitchpref,.7f)
        set(value) = preferences.edit().putFloat(pitchpref, value).apply()

    var pitchselection: Float
        get() = preferences.getFloat(pitchpref,1f)
        set(value) = preferences.edit().putFloat(pitchpref, value).apply()
    var AppRated: Boolean
        get() = preferences.getBoolean(isapprated, false)
        set(backbuttonvalue) = preferences.edit().putBoolean(isapprated, backbuttonvalue).apply()

    var permissionstatus: Boolean
        get() = preferences.getBoolean(permissionallowed, false)
        set(backbuttonvalue) = preferences.edit().putBoolean(permissionallowed, backbuttonvalue).apply()
}