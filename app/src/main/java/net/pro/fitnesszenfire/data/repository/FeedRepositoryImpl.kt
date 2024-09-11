package net.pro.fitnesszenfire.data.repository

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import net.pro.fitnesszenfire.domain.model.Post
import net.pro.fitnesszenfire.domain.repository.FeedRepository
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
        private val firestore: FirebaseFirestore,
        private val storage: FirebaseStorage
) : FeedRepository {

    override fun getPostsFromFirestore(onSuccess: (List<Post>) -> Unit, onError: (Exception) -> Unit) {
        firestore.collection("posts")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val postList = querySnapshot.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(Post::class.java)
                }
                onSuccess(postList)
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }


    override fun uploadImage(uri: Uri, onSuccess: (String) -> Unit, onError: (Exception) -> Unit) {
        val storageRef = storage.reference.child("images/${uri.lastPathSegment}")
        storageRef.putFile(uri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    onSuccess(downloadUrl.toString())
                }
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }

    override fun savePostToFirestore(post: Post, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        firestore.collection("posts").add(post)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onError(it) }
    }
}
