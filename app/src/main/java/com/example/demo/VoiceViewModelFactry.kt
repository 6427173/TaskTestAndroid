package com.spellchecker.speakandtranslate.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spellchecker.speakandtranslate.dataclasses.VoicetranslateModel
import com.spellchecker.speakandtranslate.viewmodels.SharedViewModel

class VoiceViewModelFactry(private val voicerepositry:VoiceRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SharedViewModel(voicerepositry) as T
    }
}