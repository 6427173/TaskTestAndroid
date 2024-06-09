package com.spellchecker.speakandtranslate.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version =1,entities = [Voicetranslation::class])
abstract class VoicetransDatabase: RoomDatabase() {

    abstract fun voiceTanslationDao():VoiceTranslationDao

    companion object {

        private var INSTANCE: VoicetransDatabase? = null

        fun getDatabase(context: Context): VoicetransDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        VoicetransDatabase::class.java,
                        "Voicetranslation.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}