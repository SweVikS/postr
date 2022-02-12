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
    fun Home(titleModel: Model): String {
        titleModel.addAttribute("pagetitle", "Postr - Home")
        return "home"
    }

    @GetMapping("")
    fun FallbackRedirect(titleModel: Model): String {
        titleModel.addAttribute("pagetitle", "Postr - Home")
        return "home"
    }

    @GetMapping("timeline")
    fun Timeline(postModel: Model, titleModel: Model): String {
        postModel.addAttribute("postList", postService.findAll())
        titleModel.addAttribute("pagetitle", "Postr - Timeline")
        return "timeline"
    }

    @GetMapping("userlistpage")
    fun UserList(userListModel: Model, titleModel: Model): String {
        userListModel.addAttribute("userList", userService.getAllUsers())
        titleModel.addAttribute("pagetitle", "Postr - User List")
        return "userlistpage"
    }


//    @GetMapping("profilepage")
//    fun ProfilePage(model: Model, principal: Principal, titleModel: Model): String {
//        val user = userService.getUserByName(principal.name)
//        model.addAttribute("profile", user.profile)
//        titleModel.addAttribute("pagetitle","Postr - Profile")
//        return "profilepage"
//    }

    @GetMapping("editprofile")
    fun ProfilePageEdit(profileModel: Model, titleModel: Model, principal: Principal): String {
        val user = userService.getUserByName(principal.name)
        profileModel.addAttribute("profile", user.profile)
        titleModel.addAttribute("pagetitle", "Postr - Edit Profile")
        return "editprofile"
    }

    @GetMapping("profilepage/{username}")
    fun ProfilePage(
        profileModel: Model,
        titleModel: Model,
        signedInUserModel: Model,
        @PathVariable username: String,
        principal: Principal
    ): String {
        val user = userService.getUserByName(username)
        profileModel.addAttribute("profile", user.profile)
        titleModel.addAttribute("pagetitle", "Postr - Profile")

        val sameUser = username == principal.name
        signedInUserModel.addAttribute("signedInUser", sameUser)
        return "profilepage"
    }
}