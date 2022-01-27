package app.postr.controllers

import app.postr.models.ProfileRepo
import app.postr.models.UserRepo
import app.postr.services.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.security.Principal

@Controller
class PostController(
    @Autowired val postService: PostService
) {

    @PostMapping("timeline/post")
    fun postPost(
        @ModelAttribute
        postDTO: postDTO, principal: Principal
    ): String {
        postService.savePost(postDTO,principal.name)
        return "redirect:/profilepage"
    }
}