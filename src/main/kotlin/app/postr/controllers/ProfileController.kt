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

/**
 * Controller Class handling POST requests for editing a User's Profile. Has UserService
 * and ProfileService Autowired for use in functions.
 */
@Controller
class ProfileController(
    @Autowired val profileService: ProfileService,
    @Autowired val userService:UserService
) {

    /**
     * Mapped to POST Request "profilepage/edit". Has titleModel Model as in parameter for dynamically setting
     * HTML page <title> in Thymeleaf fragment head.html. Has profileModel Model for sending the logged in User's
     * Profile to HTML document. Has Principal for retrieving username of logged in User. Boolean sameUser is used to determine if logged in User
     * is same as @PathVariable username. If not, editing profile is disabled in HTML document. When triggered,
     * th:value description, email, phoneNr and companyRole in HTML document are sent to profileDTO for use in ProfileService function.
     * profileService.saveProfile function is called to persist updated Profile in database.
     * Returns profilepage.html to client.
     */
    @PostMapping("/profilepage/edit")
    fun profileEdit(
        @ModelAttribute
        profileDTO: profileDTO,
        model: Model,
        principal: Principal
    ): String {
        profileService.saveProfile(profileDTO,principal.name)

        val user = userService.getUserByName(principal.name)
        model.addAttribute("profile", user?.profile)
        model.addAttribute("pagetitle", "Postr - Profile")

        val sameUser = user?.username == principal.name
        model.addAttribute("signedInUser", sameUser)
        return "profilepage"
    }
}