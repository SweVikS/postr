package app.postr.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal

@Controller
class MainController {


    //List of strings added
//    @GetMapping("home")
//    fun Home(model : Model) : String {
//        model.addAttribute("postList", listOf("Hej", "på", "dig"))
//        return "home"
//    }

    @GetMapping("home")
    fun Home() : String {
        return "home"
    }

    @GetMapping("")
    fun FallbackRedirect() : String {
        return "home"
    }

    @GetMapping("timeline")
    fun Timeline(principal : Principal) : String {
        println(principal.name)
        return "timeline"
    }

    @GetMapping("profile")
    fun Profile(principal : Principal) : String {

        return "profile"
    }
}