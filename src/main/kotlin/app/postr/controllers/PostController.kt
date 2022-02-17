package app.postr.controllers

import app.postr.services.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.security.Principal

/**
 * Controller Class for handling POST requests for Posts. Has PostService Autowired for use in function.
 */
@Controller
class PostController(
    @Autowired val postService: PostService
) {

/**
 * Mapped to POST Request "timeline/post". When triggered, th:value heading and body in HTML document are sent to postDTO for use in PostService function. Principal is used
 * for retrieving username of logged in User. postService.savePost function is called to persist new Post in database. Returns redirection to timeline.html to client.
 */
    @PostMapping("timeline/post")
    fun postPost(
        @ModelAttribute
        postDTO: postDTO, principal: Principal
    ): String {
        postService.savePost(postDTO,principal.name)
        return "redirect:/timeline"
    }
}