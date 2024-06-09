package com.spellchecker.speakandtranslate.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.spellchecker.speakandtranslate.database.VoiceRepository
import com.spellchecker.speakandtranslate.database.Voicetranslation
import com.spellchecker.speakandtranslate.dataclasses.VoicetranslateModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SharedViewModel(private val voicerepositry:VoiceRepository):ViewModel() {


    fun getvoicetranslation():LiveData<List<Voicetranslation>>{
        return voicerepositry.gettranslations()
    }
    fun insertVoiceTranslation(voicetrans:Voicetranslation){
        viewModelScope.launch(Dispatchers.IO) {
            voicerepositry.inserttrnaslation(voicetrans)
        }

    }


    fun deleteVoiceTranslation(voicetrans:Voicetranslation){
        viewModelScope.launch(Dispatchers.IO) {
            voicerepositry.deleteData(voicetrans)
        }

    }
}