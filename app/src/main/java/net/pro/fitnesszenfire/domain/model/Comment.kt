package net.pro.fitnesszenfire.domain.model

data class Comment(
        var commentId:String="",
        var comment:String="",
        var timeStamp:String="",
        var userId:String="",
        var userImage:String="",
        var userName:String=""

)