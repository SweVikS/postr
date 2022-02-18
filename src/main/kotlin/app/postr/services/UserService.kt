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
) : IUserService {

    /**
     * Encrypts password to be saved in database.
     */
    private val bCryptEncoder = BCryptPasswordEncoder()


    override fun registerNewUser(userDTO: UserDTO?): MyUser? {

        val encryptedPassword = bCryptEncoder.encode(userDTO?.password)

        if (emailExist(userDTO?.email)) {
            throw UserAlreadyExistException(
                "There is an account with that email address: "
                        + userDTO?.email
            );
        }

        var newProfile = Profile(description = "")
        var role: Role? = roleRepo.findByName("ROLE_USER")
        var roleCollection: MutableCollection<Role> :
        roleCollection.add(role)
        var user: MyUser? = null
        user?.username = userDTO?.username
        user?.password = encryptedPassword
        user?.email = userDTO?.email
        user?.profile = newProfile
        user?.roles = roleCollection

        return userRepo.save(newUser)
    }

    class UserAlreadyExistException : RuntimeException {
        constructor() : super() {}
        constructor(message: String?, cause: Throwable?) : super(message, cause) {}
        constructor(message: String?) : super(message) {}
        constructor(cause: Throwable?) : super(cause) {}

        companion object {
            private const val serialVersionUID = 5861310537366287163L
        }
    }

    fun emailExist(email: String?): Boolean {
        return userRepo.findByEmail(email) != null
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

interface IUserService {
    fun registerNewUser(userDto: UserDTO?): MyUser?
}

/**
 *Data Transfer Object used for sending registration credentials to UserService
 */
class SignupDTO(val username: String, val password: String)