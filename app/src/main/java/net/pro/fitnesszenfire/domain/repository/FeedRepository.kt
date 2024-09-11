package net.pro.fitnesszenfire.domain.repository

import android.net.Uri
import net.pro.fitnesszenfire.domain.model.Post

interface FeedRepository {

    fun getPostsFromFirestore(onSuccess: (List<Post>) -> Unit, onError: (Exception) -> Unit)
    fun uploadImage(uri: Uri, onSuccess: (String) -> Unit, onError: (Exception) -> Unit)
    fun savePostToFirestore(post: Post, onSuccess: () -> Unit, onError: (Exception) -> Unit)
}