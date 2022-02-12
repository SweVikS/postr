package app.postr.controllers

import app.postr.models.ProfileRepo
import app.postr.models.UserRepo
import app.postr.services.ProfileService
import app.postr.services.UserService
import app.postr.services.profileDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal

@Controller
class ProfileController(
    @Autowired val profileService: ProfileService,
    @Autowired val userService:UserService
) {

    @PostMapping("/profilepage/edit")
    fun profileEdit(
        @ModelAttribute
        profileDTO: profileDTO,
        profileModel: Model,
        titleModel: Model,
        signedInUserModel: Model,
        principal: Principal
    ): String {
        profileService.saveProfile(profileDTO,principal.name)

        val user = userService.getUserByName(principal.name)
        profileModel.addAttribute("profile", user.profile)
        titleModel.addAttribute("pagetitle", "Postr - Profile")

        val sameUser = user.username == principal.name
        signedInUserModel.addAttribute("signedInUser", sameUser)
        return "profilepage"
    }
}