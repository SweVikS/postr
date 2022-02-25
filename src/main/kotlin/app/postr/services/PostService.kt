package app.postr.services

import app.postr.models.Post
import app.postr.models.PostRepo
import app.postr.models.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

/**
 * Service Class for creating layer between Controller functions and Repositories.
 * Has UserService, PostRepo and UserRepo Autowired for use in functions.
 */
@Service
class PostService(
    @Autowired
    val userService: UserService,
    @Autowired
    val postRepo: PostRepo,
    @Autowired
    val userRepo: UserRepo
) {
    /**
     * Persists a new Post object in database. Recieves postDTO and username from Controller function.
     */
    fun savePost(postDTO: postDTO, username: String) {

        val user = userService.getUserByName(username) ?: throw NotFoundException()
        val newPost = Post(heading = postDTO.heading, body = postDTO.body, user = user)

        user.posts?.add(newPost)

        postRepo.save(newPost)
        userRepo.save(user)

    }

    fun findAll(): MutableIterable<Post> {
        return postRepo.findAll()
    }
}

/**
 * Data Transfer Object for encapsulating Post object data.
 */
class postDTO(var heading: String, var body: String)