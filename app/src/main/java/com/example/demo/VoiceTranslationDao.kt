package com.spellchecker.speakandtranslate.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VoiceTranslationDao {
    @Query("SELECT * FROM voicetranslation ORDER BY t_id DESC")


    fun getAll(): LiveData<List<Voicetranslation>>

    @Insert
    fun  insertallhistory(vararg saveHistory: Voicetranslation)

    @Delete
    fun delete(dataitem: Voicetranslation)

    @Query("DELETE FROM voicetranslation")
    fun deleteAll()
}

