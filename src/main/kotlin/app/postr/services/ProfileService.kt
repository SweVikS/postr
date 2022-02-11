package app.postr.services

import app.postr.models.ProfileRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProfileService(
    @Autowired
    val profileRepo: ProfileRepo,
    @Autowired
    val userService: UserService
) {
    fun saveProfile(profileDTO: profileDTO, username: String) {

        val user = userService.getUserByName(username)

        user.profile.description = profileDTO.description
        user.profile.email = profileDTO.email
        user.profile.phoneNr=profileDTO.phoneNr
        user.profile.companyRole = profileDTO.companyRole

        profileRepo.save(user.profile)
    }
}

class profileDTO(var description: String?, var email: String?, val phoneNr: String?, val companyRole: String?)