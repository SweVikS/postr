package app.postr.services

import app.postr.models.ProfileRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Service Class for creating layer between Controller functions and Repositories.
 * Has UserService and ProfileRepo Autowired for use in functions.
 */
@Service
class ProfileService(
    @Autowired
    val profileRepo: ProfileRepo,
    @Autowired
    val userService: UserService
) {

    /**
     * Persists an updated Profile object in database. Recieves ProfileDTO and username from Controller function.
     * Retrieves logged in User from UserService and sets fields of ProfileDTO to the logged in User's Profile fields.
     */
    fun saveProfile(profileDTO: ProfileDTO, username: String) {

        val user = userService.getUserByName(username)

        user?.profile!!.description = profileDTO.description
        user.profile!!.email = profileDTO.email
        user.profile!!.phoneNr=profileDTO.phoneNr
        user.profile!!.companyRole = profileDTO.companyRole

        profileRepo.save(user.profile!!)
    }
}

/**
 * Data Transfer Object for encapsulating Profile object data.
 */
class ProfileDTO(var description: String?, var email: String?, val phoneNr: String?, val companyRole: String?)