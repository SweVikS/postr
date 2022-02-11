package app.postr.controllers

import app.postr.services.SignupDTO
import app.postr.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

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
    fun Signin(titleModel: Model): String {
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

    /**
     * Mapped to GET Request "signup". Has titleModel Model as in parameter for dynamically setting
     * HTML page <title> in Thymeleaf fragment head.html. Returns signup.html to client.
     */
    @GetMapping("signup")
    fun signup(titleModel: Model): String {
        titleModel.addAttribute("pagetitle", "Postr - Sign up")
        return "signup"
    }

    /**
     *Mapped to POST Request "signup". Has signupDTO as in parameter,sends DTO to UserService.
     * Returns redirection to home.html to client.
     */
    @PostMapping("signup")
    fun signupPost(
        @ModelAttribute
        signupDTO: SignupDTO
    ): String {
        userService.saveNewUser(signupDTO)
        return "redirect:/home"
    }
}



