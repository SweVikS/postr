package app.postr.controllers

import app.postr.services.PostService
import app.postr.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.security.Principal

/**
 * Controller Class handling GET requests of the HTML documents that comprises the application. Has UserService
 * and PostService Autowired for use in functions.
 */
@Controller
class MainController(
    @Autowired val userService: UserService,
    @Autowired val postService: PostService
) {

    /**
     * Mapped to GET Request "home". Has titleModel Model as in parameter for dynamically setting
     * HTML page <title> in Thymeleaf fragment head.html. Returns home.html to client.
     */
    @GetMapping("home")
    fun Home(titleModel: Model): String {
        titleModel.addAttribute("pagetitle", "Postr - Home")
        return "home"
    }

    /**
     * Fallback redirect in case of no path. Has titleModel Model as in parameter for dynamically setting
     * HTML page <title> in Thymeleaf fragment head.html. Returns home.html to client.
     */
    @GetMapping("")
    fun FallbackRedirect(titleModel: Model): String {
        titleModel.addAttribute("pagetitle", "Postr - Home")
        return "home"
    }

    /**
     * Mapped to GET Request "timeline". Has titleModel Model as in parameter for dynamically setting
     * HTML page <title> in Thymeleaf fragment head.html. Has postModel Model for sending all Posts to
     * HTML document. Returns timeline.html to client.
     */
    @GetMapping("timeline")
    fun Timeline(postModel: Model, titleModel: Model): String {
        postModel.addAttribute("postList", postService.findAll())
        titleModel.addAttribute("pagetitle", "Postr - Timeline")
        return "timeline"
    }

    /**
     * Mapped to GET Request "userlistpage". Has titleModel Model as in parameter for dynamically setting
     * HTML page <title> in Thymeleaf fragment head.html. Has userListModel Model for sending all Users to HTML document.
     * Returns userlistpage.html to client.
     */
    @GetMapping("userlistpage")
    fun UserList(userListModel: Model, titleModel: Model): String {
        userListModel.addAttribute("userList", userService.getAllUsers())
        titleModel.addAttribute("pagetitle", "Postr - User List")
        return "userlistpage"
    }


    /**
     * Mapped to GET Request "editprofile". Has titleModel Model as in parameter for dynamically setting
     * HTML page <title> in Thymeleaf fragment head.html. Has profileModel Model for sending the logged in User's
     * Profile to HTML document. Has Principal for retrieving username of logged in User.
     * Returns editprofile.html to client.
     */
    @GetMapping("editprofile")
    fun ProfilePageEdit(profileModel: Model, titleModel: Model, principal: Principal): String {
        val user = userService.getUserByName(principal.name)
        profileModel.addAttribute("profile", user.profile)
        titleModel.addAttribute("pagetitle", "Postr - Edit Profile")
        return "editprofile"
    }

    /**
     * Mapped to GET Request "profilepage/{username}". Has titleModel Model as in parameter for dynamically setting
     * HTML page <title> in Thymeleaf fragment head.html. Has profileModel Model for sending the logged in User's
     * Profile to HTML document. Has Principal for retrieving username of logged in User. @PathVariable username is for retrieving username
     * from GET request. Boolean sameUser is used to determine if logged in User is same as @PathVariable username. If not, editing profile
     * is disabled in HTML document.
     * Returns profilepage.html to client.
     */
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