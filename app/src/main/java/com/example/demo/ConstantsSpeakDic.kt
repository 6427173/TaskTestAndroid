package com.spellchecker.speakandtranslate.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

class ConstantsSpeakDic : TextToSpeech.OnInitListener
{

    private var textForSpeak: String = ""

    companion object
    {

        var inputLanguageCode: String = ""
        var outputCodeForSpeak: String = "eng"
        var pitch = 1f
        var speed = .7f
        private lateinit var tts: TextToSpeech

    }

    fun ttsInitialization(context: Context)
    {
        tts = TextToSpeech(context, this@ConstantsSpeakDic)
    }

    fun forSpeak(textForSpeak: String, outputCode: String, context: Context)
    {
        this.textForSpeak = textForSpeak

        tts = TextToSpeech(context, this@ConstantsSpeakDic)

        outputCodeForSpeak = outputCode
        tts.language = Locale(outputCode)
        if (!tts.isSpeaking)
        {
            tts.setPitch(pitch)
            tts.setSpeechRate(speed)
            tts.speak(textForSpeak, TextToSpeech.QUEUE_ADD, null, null)
        }
        else
        {
            tts.stop()
        }
    }

    override fun onInit(status: Int)
    {
        if (status != TextToSpeech.ERROR)
        {
            tts.language = Locale(outputCodeForSpeak)
            tts.speak(textForSpeak, TextToSpeech.QUEUE_ADD, null, null)
        }

    }

    fun getTts(): TextToSpeech
    {
        return tts
    }

}