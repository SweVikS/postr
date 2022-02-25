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
    @GetMapping("/home")
    fun Home(model: Model): String {
        model.addAttribute("pagetitle", "Postr - Home")
        return "home"
    }

    /**
     * Fallback redirect in case of no path. Has titleModel Model as in parameter for dynamically setting
     * HTML page <title> in Thymeleaf fragment head.html. Returns home.html to client.
     */
    @GetMapping("/")
    fun FallbackRedirect(model: Model): String {
        model.addAttribute("pagetitle", "Postr - Home")
        return "home"
    }

    /**
     * Mapped to GET Request "timeline". Has titleModel Model as in parameter for dynamically setting
     * HTML page <title> in Thymeleaf fragment head.html. Has postModel Model for sending all Posts to
     * HTML document. Returns timeline.html to client.
     */
    @GetMapping("/timeline")
    fun Timeline(model: Model): String {
        model.addAttribute("postList", postService.findAll())
        model.addAttribute("pagetitle", "Postr - Timeline")
        return "timeline"
    }

    /**
     * Mapped to GET Request "userlistpage". Has titleModel Model as in parameter for dynamically setting
     * HTML page <title> in Thymeleaf fragment head.html. Has userListModel Model for sending all Users to HTML document.
     * Returns userlistpage.html to client.
     */
    @GetMapping("userlistpage")
    fun UserList(model: Model): String {
        model.addAttribute("userList", userService.getAllUsers())
        model.addAttribute("pagetitle", "Postr - User List")
        return "userlistpage"
    }


    /**
     * Mapped to GET Request "editprofile". Has titleModel Model as in parameter for dynamically setting
     * HTML page <title> in Thymeleaf fragment head.html. Has profileModel Model for sending the logged in User's
     * Profile to HTML document. Has Principal for retrieving username of logged in User.
     * Returns editprofile.html to client.
     */
    @GetMapping("editprofile")
    fun ProfilePageEdit(model: Model, principal: Principal): String {
        val user = userService.getUserByName(principal.name)
        model.addAttribute("profile", user?.profile)
        model.addAttribute("pagetitle", "Postr - Edit Profile")
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
       model:Model,
        @PathVariable username: String,
        principal: Principal
    ): String {
        val user = userService.getUserByName(username)
        model.addAttribute("profile", user?.profile)
        model.addAttribute("pagetitle", "Postr - Profile")

        val sameUser = username == principal.name
        model.addAttribute("signedInUser", sameUser)
        return "profilepage"
    }

    /**
     * Mapped to GET Request "signin". Has titleModel Model as in parameter for dynamically setting
     * HTML page <title> in Thymeleaf fragment head.html. Returns signin.html to client.
     */
    @GetMapping("signin")
    fun SigninGet(
        model: Model
    ): String {
        model.addAttribute("successfulRegistration", false)
        model.addAttribute("pagetitle", "Postr - Sign in")
        return "signin"
    }
}