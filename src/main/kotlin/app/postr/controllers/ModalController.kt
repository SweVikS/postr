package app.postr.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class ModalController {

    @GetMapping("modals/signupmodal")
    fun GetSignupModal() : String{
        return "signupmodal"
    }
}