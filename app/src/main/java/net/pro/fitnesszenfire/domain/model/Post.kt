package net.pro.fitnesszenfire.domain.model

import java.util.UUID

data class Post(
        var id: String = "", // ID duy nhất của bài đăng
        var authorId: String = "",
        var imageUrl: List<Image> = emptyList(),                 // Danh sách ảnh kèm theo
        var caption: String = "",                   // Chú thích của bài đăng
        val location: String = "",
        var postFans: String? = "Anyone",           // Ai có thể xem bài đăng (fan)
        var postType: String? = "article",          // Loại bài đăng (bài viết, hình ảnh, video, ...)
        var postAttachment: String? = "",           // Tệp đính kèm (nếu có)
        var postTime: String = "",                  // Thời gian đăng bài
        var languageCode: String = "und",           // Mã ngôn ngữ của bài đăng
        var translatedContent: String = "",         // Nội dung đã được dịch (nếu có)

        // Thêm thông tin về những người đã tương tác
        var postLikes: Int = 0, // Số lượng likes
        var postComments: Int = 0, // Số lượng comments
        var likedBy: List<String> = emptyList(), // List of user IDs
        var comments: List<String> = emptyList() // List of comment IDs

)

data class Image(
    val id: String = "",
    val url: String = "",
) {

}