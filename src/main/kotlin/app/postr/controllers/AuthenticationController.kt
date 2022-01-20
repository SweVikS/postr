package app.postr.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthenticationController {

    @GetMapping("signin")
    fun Signin() : String {
        return "signin"
    }

    @GetMapping("signin-failure")
    fun SigninFailure() : String {
        return "signin-failure"
    }

    @GetMapping("signup")
    fun Signup() : String {
        return "signup"
    }
}