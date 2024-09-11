package net.pro.fitnesszenfire.domain.model

import com.google.firebase.firestore.PropertyName


data class User(
        @set:PropertyName("uid")
        @get:PropertyName("uid")
        var id: String? = "",

        @set:PropertyName("name")
        @get:PropertyName("name")
        var userName: String? = "",

        @set:PropertyName("email")
        @get:PropertyName("email")
        var email: String? = "",

        @set:PropertyName("photoUrl")
        @get:PropertyName("photoUrl")
        var avatar: String? = "",

        var followers: List<String>? = null,
        var following: List<String>? = null,
)