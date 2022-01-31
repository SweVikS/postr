package app.postr.controllers

import app.postr.services.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
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
        return "redirect:/timeline"
    }

//    @GetMapping("timeline/all")
//    fun allPosts(model: Model): String? {
//        model.addAttribute("posts", postService.findAll())
//        return "timeline"
//    }
}