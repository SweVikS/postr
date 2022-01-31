package app.postr.controllers

import app.postr.services.PostService
import app.postr.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.security.Principal

@Controller
class MainController(
    @Autowired val userService: UserService,
    @Autowired val postService: PostService
) {

    @GetMapping("home")
    fun Home(): String {
        return "home"
    }

    @GetMapping("")
    fun FallbackRedirect(): String {
        return "home"
    }

    @GetMapping("timeline")
    fun Timeline(model: Model): String {
        model.addAttribute("postList", postService.findAll())
        return "timeline"
    }

//    @GetMapping("home")
//    fun Home(model: Model): String {
//        model.addAttribute("postList", listOf("Hej", "på", "dig"))
//        return "home"
//    }

    @GetMapping("profilepage")
    fun ProfilePage(model: Model, principal: Principal): String {
        val user = userService.getUserByName(principal.name)
        model.addAttribute("description", user.profile.description)
        return "profilepage"
    }

    @GetMapping("profilepage/{username}")
    fun ProfilePageSpec(model: Model, @PathVariable("username") username: String): String {
        val user = userService.getUserByName(username)
        model.addAttribute("description", user.profile.description)
        return "profilepage"
    }
}