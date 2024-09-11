/**
 *
 *	MIT License
 *
 *	Copyright (c) 2023 Gautam Hazarika
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a copy
 *	of this software and associated documentation files (the "Software"), to deal
 *	in the Software without restriction, including without limitation the rights
 *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *	copies of the Software, and to permit persons to whom the Software is
 *	furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in all
 *	copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *	SOFTWARE.
 *
 **/

package net.pro.fitnesszenfire.presentation.feed

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import net.pro.fitnesszenfire.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.pro.fitnesszenfire.domain.model.Image
import net.pro.fitnesszenfire.domain.model.Post
import net.pro.fitnesszenfire.domain.model.User
import net.pro.fitnesszenfire.domain.repository.FeedRepository
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class FeedViewModel @Inject constructor(
        private val postRepository: FeedRepository,
        private val firestore: FirebaseFirestore
) : ViewModel() {

    var isLoading by mutableStateOf(false)
    var postUploadSuccess by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var posts by mutableStateOf<List<Post>>(emptyList())
        private set


    init {
        getPosts() // Lấy danh sách bài đăng khi ViewModel được khởi tạo
    }
    private fun getPosts() {
        postRepository.getPostsFromFirestore(
                onSuccess = { fetchedPosts ->
                    posts = fetchedPosts
                },
                onError = { exception ->
                    Log.e("FeedViewModel", "Error fetching posts: ", exception)
                }
        )
    }

    fun uploadPost(
            uri: Uri,
            caption: String,
            navController: NavHostController,
            authorId: String,
            location: String,
            postTime: String
    ) {

        navController.popBackStack()

        isLoading = true
        postRepository.uploadImage(uri, onSuccess = { imageUrl ->
            val imageList = listOf(Image(id = generateImageId(), url = imageUrl))
            val post = Post(
                    caption = caption,
                    imageUrl = imageList,
                    authorId = authorId,
                    location = location,
                    postTime = postTime
            )

            postRepository.savePostToFirestore(post, onSuccess = {
                isLoading = false
                postUploadSuccess = true

                getPosts() // Gọi lại để làm mới danh sách bài đăng
            }, onError = { exception ->
                isLoading = false
                errorMessage = exception.message
            })
        }, onError = { exception ->
            isLoading = false
            errorMessage = exception.message
        })
    }


    fun getUserById(authorId: String, onSuccess: (User) -> Unit, onError: (Exception) -> Unit) {
        firestore.collection("users").document(authorId)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val user = documentSnapshot.toObject(User::class.java)

                Log.e("usercheck","$user")
                user?.let { onSuccess(it) } ?: onError(Exception("User not found"))
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }



    fun generateImageId(): String {
        return UUID.randomUUID().toString()
    }

}