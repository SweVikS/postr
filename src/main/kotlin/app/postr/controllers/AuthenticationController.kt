package app.postr.controllers

import app.postr.services.SignupDTO
import app.postr.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller

class AuthenticationController(@Autowired
val userService: UserService) {

    @GetMapping("signin")
    fun Signin() : String {
        return "signin"
    }

    @GetMapping("signin-failure")
    fun SigninFailure() : String {
        return "signin-failure"
    }

    @GetMapping("signup")
    fun signup() : String {
        return "signup"
    }
    @PostMapping("signup")
    fun signupPost(
        @ModelAttribute
        signupDTO: SignupDTO) : String {


        userService.saveNewUser(signupDTO)
        return "redirect:/timeline"
    }
}



