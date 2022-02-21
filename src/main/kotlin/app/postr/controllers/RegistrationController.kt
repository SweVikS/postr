package app.postr.controllers

import app.postr.dtos.UserDTO
import app.postr.models.MyUser
import app.postr.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.context.request.WebRequest
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@Controller
class RegistrationController(
    @Autowired val userService: UserService
) {

    @GetMapping("signup")
    fun signupGet(request: WebRequest?, userDTOModel: Model, titleModel: Model): String? {
        val userDTO = UserDTO()
        userDTOModel.addAttribute("user", userDTO)
        titleModel.addAttribute("pagetitle", "Postr - Sign up")
        return "signup"
    }


    @PostMapping("signup")
    fun signupPost(
        @Valid
        @ModelAttribute("user")
        userDTO : UserDTO,
        result: BindingResult
//        request : HttpServletRequest,
//        errors : Errors,
//        msgModel: Model
    ): String {
        if (result.hasErrors()) {
            return "signup";
        }

            userService.registerNewUser(userDTO)

        return "redirect:/signin"
    }
}