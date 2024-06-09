package com.spellchecker.speakandtranslate.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "voicetranslation")
data class Voicetranslation(@PrimaryKey(autoGenerate = true) var t_id:Int,
                      @ColumnInfo(name="inputword") var inputword:String?,
                            @ColumnInfo(name="outword") var outword:String?,
                            @ColumnInfo(name = "inputlang") var inputlang: Int,
                            @ColumnInfo(name = "outputlang") var outputlang: Int,
                            @ColumnInfo(name = "typeval") var typeval: Int
)
