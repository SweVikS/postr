package app.postr.services

import app.postr.dtos.UserDTO
import app.postr.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

/**
 * Handles User object creation and saves the entity in database. UserRepo is autowired into the class
 * for persisting data in database.
 */
@Service
@Transactional
class UserService(
    @Autowired
    val userRepo: UserRepo,
    @Autowired
    val roleRepo: RoleRepo
) {

    /**
     * Encrypts password to be saved in database.
     */
    private val bCryptEncoder = BCryptPasswordEncoder()

    fun registerNewUser(userDTO: UserDTO): MyUser? {
        val encryptedPassword = bCryptEncoder.encode(userDTO.password)

//        if (emailExist(userDTO.email)) {
//            throw UserAlreadyExistException(
//                "There is an account with that email address: "
//                        + userDTO.email
//            );
//        }

        var newProfile = Profile(description = "")
        var role: Role? = roleRepo.findByName("ROLE_USER")

        var user = MyUser(
            username = userDTO.username,
            password = encryptedPassword,
            email = userDTO.email,
            profile = newProfile
        )
        if (role != null) {
            user.roles?.add(role)
        }


        return userRepo.save(user)
    }


//    class UserAlreadyExistException : RuntimeException {
//        constructor() : super() {}
//        constructor(message: String?, cause: Throwable?) : super(message, cause) {}
//        constructor(message: String?) : super(message) {}
//        constructor(cause: Throwable?) : super(cause) {}
//
//        companion object {
//            private const val serialVersionUID = 5861310537366287163L
//        }
//    }

//    private fun emailExist(email: String): Boolean {
//        return userRepo.findByEmail(email) != null
//    }
//    fun emailExist(email: String): Boolean {
//        var emailExist: Boolean = false
//        try {
//            userRepo.findByEmail(email)
//
//        } catch (e: EmptyResultDataAccessException) {
//            emailExist == true
//        }
//
//        return emailExist
//    }

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

//interface IUserService {
//    fun registerNewUser(userDTO: UserDTO?): MyUser?
//}

/**
 *Data Transfer Object used for sending registration credentials to UserService
 */
class SignupDTO(val username: String, val password: String)