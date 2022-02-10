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
    fun saveProfile(profileDTO: profileDTO, username : String) {

        val user = userService.getUserByName(username)

        user.profile.description=profileDTO.description

        profileRepo.save(user.profile)
    }
}
class profileDTO(val description: String)