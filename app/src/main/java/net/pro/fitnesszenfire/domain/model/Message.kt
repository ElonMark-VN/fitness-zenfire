package net.pro.fitnesszenfire.domain.model


data class Message(
        var body:String="",
        var time:String="",
        var sender:String="",
        var receiver:String="",
        var languageCode:String="und",
        var translatedContent:String=""
)