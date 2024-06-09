package com.spellchecker.speakandtranslate.dataclasses

data class VoicetranslateModel
    (var inputdata:String?,
     var outputdata:String?,
     var inputlang:Int,
     var outputlang:Int,
     var inputtype:Int)