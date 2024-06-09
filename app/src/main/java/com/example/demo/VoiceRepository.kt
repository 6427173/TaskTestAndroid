package com.spellchecker.speakandtranslate.database

import androidx.lifecycle.LiveData

class VoiceRepository(val voiceDao:VoiceTranslationDao){

    fun gettranslations():LiveData<List<Voicetranslation>>{
        return voiceDao.getAll()
    }
    suspend fun inserttrnaslation(voicedata:Voicetranslation){
        voiceDao.insertallhistory(voicedata)
    }
    suspend fun deleteData(voicedata:Voicetranslation){
        voiceDao.delete(voicedata)
    }


}