package app.postr.services

import app.postr.models.MyUser
import app.postr.models.Profile
import app.postr.models.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    @Autowired
    val userRepo: UserRepo
) {
    private val bCryptEncoder = BCryptPasswordEncoder()

    fun saveNewUser(signupDTO: SignupDTO) {

        val encryptedPassword = bCryptEncoder.encode(signupDTO.password)


        val newProfile = Profile(description = "")

        val newUser = MyUser(
            username = signupDTO.username,
            password = encryptedPassword,
            profile = newProfile,
            posts = mutableListOf()
        )
        try {
            userRepo.save(newUser)
        } catch (ex: Exception) {
//TODO Implement ExceptionHandler
        }

    }

//    fun getUserFromPrincipal(principal: MyUserPrincipal): Optional<MyUser>? {
//
//        return principal.user?.id?.let { userRepo.findById(it) }
//    }


    fun getUserByName(username: String): MyUser {
        return userRepo.findByUsername(username)
    }
}

class SignupDTO(val username: String, val password: String)