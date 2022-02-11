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
    fun Home(titleModel : Model): String {
        titleModel.addAttribute("pagetitle","Postr - Home")
        return "home"
    }

    @GetMapping("")
    fun FallbackRedirect(titleModel: Model): String {
        titleModel.addAttribute("pagetitle","Postr - Home")
        return "home"
    }

    @GetMapping("timeline")
    fun Timeline(postModel: Model, titleModel: Model): String {
        postModel.addAttribute("postList", postService.findAll())
        titleModel.addAttribute("pagetitle","Postr - Timeline")
        return "timeline"
    }


    @GetMapping("profilepage")
    fun ProfilePage(model: Model, principal: Principal, titleModel: Model): String {
        val user = userService.getUserByName(principal.name)
        model.addAttribute("description", user.profile.description)
        titleModel.addAttribute("pagetitle","Postr - Profile")
        return "profilepage"
    }

    @GetMapping("profilepage/{username}")
    fun ProfilePageSpec(model: Model, @PathVariable("username") username: String): String {
        val user = userService.getUserByName(username)
        model.addAttribute("description", user.profile.description)
        return "profilepage"
        
    }
}