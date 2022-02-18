package app.postr.controllers

import app.postr.dtos.UserDTO
import app.postr.models.MyUser
import app.postr.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.context.request.WebRequest
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

class RegistrationController(
    @Autowired val userService: UserService
) {

    @GetMapping("signup")
    fun signupGet(request: WebRequest?, userDTOModel: Model, titleModel: Model): String? {
        val userDto = UserDTO()
        userDTOModel.addAttribute("user", userDto)
        titleModel.addAttribute("pagetitle", "Postr - Sign up")
        return "signup"
    }


    @PostMapping("signup")
    fun signupPost(
        @ModelAttribute("user")
        @Valid
        userDTO : UserDTO,
        request : HttpServletRequest,
        errors : Errors,
        msgModel: Model
    ): String {


        try {
            val registered: MyUser? = userService.registerNewUser(userDTO)
        } catch (uaeEx: UserService.UserAlreadyExistException) {
            msgModel.addAttribute("message", "An account for that username/email already exists.")
        return "signup"
        }
        return "redirect:/signin"
    }
}