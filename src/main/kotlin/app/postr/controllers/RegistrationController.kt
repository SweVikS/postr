package app.postr.controllers

import app.postr.dtos.SignupDTO
import app.postr.models.MyUser
import app.postr.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.validation.Valid

@Controller
class RegistrationController(
    @Autowired val userService: UserService
) {

    @GetMapping("signup")
    fun signupGet(request: WebRequest?, userDTOModel: Model, titleModel: Model): String? {
        val signupDTO = SignupDTO()
        userDTOModel.addAttribute("user", signupDTO)
        titleModel.addAttribute("pagetitle", "Postr - Sign up")
        return "signup"
    }


    @PostMapping("signup")
    fun signupPost(
        @Valid
        @ModelAttribute("user")
        signupDTO: SignupDTO,
        result: BindingResult,
        msgModel: Model,
        successModel: Model
    ): String {
        if (result.hasErrors()) {
            return "signup";
        }

        var user: MyUser? = userService.registerNewUser(signupDTO)

        if(user !=null){
            msgModel.addAttribute("successmsg",
                "A new account with username ${user.username} and email ${user.email} have been created. \n" +
                        "You may now sign in.")
            var successfulRegistration= true
            successModel.addAttribute("successfulRegistration", successfulRegistration)
        }
        return "signin"
    }
}