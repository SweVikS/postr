package app.postr.services

import app.postr.models.MyUser
import app.postr.models.Post
import app.postr.models.Profile
import app.postr.models.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

/**
 * Handles User object creation and saves the entity in database. UserRepo is autowired into the class
 * for persisting data in database.
 */
@Service
class UserService(
    @Autowired
    val userRepo: UserRepo
) {

    /**
     * Encrypts password to be saved in database.
     */
    private val bCryptEncoder = BCryptPasswordEncoder()

    /**
     * Receives signupDTO from Controller function. Encrypts password with bCryptEncoder.
     * Creates a new User object and populates it with credentials from DTO, along with an empty
     * Profile object and a mutable List of Post objects. Sends User object to UserRepo which
     * saves User object in database.
     */
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

    /**
     * Retrieves User object from database with UserRepo.
     */
    fun getUserByName(username: String): MyUser {
        return userRepo.findByUsername(username)
    }

    /**
     * Retrieves all User object from database with UserRepo.
     */
    fun getAllUsers(): MutableIterable<MyUser> {
        return userRepo.findAll()
    }
}

/**
 *Data Transfer Object used for sending registration credentials to UserService
 */
class SignupDTO(val username: String, val password: String)