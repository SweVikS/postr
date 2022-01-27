package app.postr.services

import app.postr.models.Post
import app.postr.models.PostRepo
import app.postr.models.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PostService(
    @Autowired
    val userService: UserService,
    @Autowired
    val postRepo: PostRepo,
    val userRepo: UserRepo


) {
    fun savePost(postDTO: postDTO, username: String) {

        val user = userService.getUserByName(username)
        val newPost = Post(heading = postDTO.heading, body = postDTO.body, user = user)
        user.posts.add(newPost)

        postRepo.save(newPost)
        userRepo.save(user)

    }
}

class postDTO(var heading: String, var body: String)