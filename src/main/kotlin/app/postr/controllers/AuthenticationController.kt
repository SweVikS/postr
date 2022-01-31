package app.postr.controllers
import app.postr.services.SignupDTO
import app.postr.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest


@Controller
class AuthenticationController(
    @Autowired
    val userService: UserService
) {

    @GetMapping("signin")
    fun Signin(titleModel : Model): String {
        titleModel.addAttribute("pagetitle","Postr - Sign in")
        return "signin"
    }

    @GetMapping("signin-failure")
    fun SigninFailure(titleModel : Model): String {
        titleModel.addAttribute("pagetitle","Postr - Sign in failure")
        return "signin-failure"
    }

    @GetMapping("signup")
    fun signup(titleModel: Model): String {
        titleModel.addAttribute("pagetitle","Postr - Sign up")
        return "signup"
    }

    @PostMapping("signup")
    fun signupPost(
        @ModelAttribute
        signupDTO: SignupDTO
    ): String {
        userService.saveNewUser(signupDTO)
        return "redirect:/home"
    }

//    fun retrieveCurrentUser(
//    ): MyUser {
//        val user: MyUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal() as MyUser;
//        return user
//    }

}



