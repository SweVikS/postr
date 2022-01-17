package app.postr.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UserController {

    @GetMapping("/signin")
    fun SignIn() : String {
        return "signIn"
    }


    @GetMapping("/signup")
    fun SignUp() : String {
        return "signUp"
    }
}