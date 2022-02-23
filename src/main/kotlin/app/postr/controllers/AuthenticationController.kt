package app.postr.controllers

import app.postr.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


/**
 * Handles User signing in/signing up processes. UserService is autowired into the class for handling
 * saving new User in signupPost Controller.
 */
@Controller
class AuthenticationController(
    @Autowired
    val userService: UserService
) {

    /**
     * Mapped to GET Request "signin". Has titleModel Model as in parameter for dynamically setting
     * HTML page <title> in Thymeleaf fragment head.html. Returns signin.html to client.
     */
    @GetMapping("signin")
    fun SigninGet(
        titleModel: Model,
        successModel: Model
    ): String {
        var successfulRegistration = false
        successModel.addAttribute("successfulRegistration", successfulRegistration)
        titleModel.addAttribute("pagetitle", "Postr - Sign in")
        return "signin"
    }

    /**
     * Mapped to GET Request "signin-failure". Has titleModel Model as in parameter for dynamically setting
     * HTML page <title> in Thymeleaf fragment head.html. Returns signin-failure.html to client.
     */
    @GetMapping("signin-failure")
    fun SigninFailure(titleModel: Model): String {
        titleModel.addAttribute("pagetitle", "Postr - Sign in failure")
        return "signin-failure"
    }
}



