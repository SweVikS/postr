package app.postr.controllers

import app.postr.models.ProfileRepo
import app.postr.models.UserRepo
import app.postr.services.ProfileService
import app.postr.services.UserService
import app.postr.services.profileDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.security.Principal

@Controller
class ProfileController(
    @Autowired val profileService: ProfileService
) {

    @PostMapping("profile/post")
    fun profilePost(
        @ModelAttribute
        profileDTO: profileDTO, principal: Principal
    ): String {
        profileService.saveProfile(profileDTO,principal.name)
        return "redirect:/profilepage"
    }
}