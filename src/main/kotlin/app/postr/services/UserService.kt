package app.postr.services

import app.postr.dtos.SignupDTO
import app.postr.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * Handles User object creation and saves the entity in database. UserRepo and RoleRepo is autowired into the class
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

    fun registerNewUser(signupDTO: SignupDTO): MyUser? {
        val encryptedPassword = bCryptEncoder.encode(signupDTO.password)

        val newProfile = Profile(description = "", email = signupDTO.email)
        val role: Role? = roleRepo.findByName("ROLE_USER")

        val user = MyUser(
            username = signupDTO.username,
            password = encryptedPassword,
            email = signupDTO.email,
            profile = newProfile
        )
        if (role != null) {
            user.roles?.add(role)
        }


        return userRepo.save(user)
    }


    /**
     * Retrieves User object from database with UserRepo.
     */
    fun getUserByName(username: String): MyUser? {
        return userRepo.findByUsername(username)
    }

    /**
     * Retrieves all User object from database with UserRepo.
     */
    fun getAllUsers(): MutableIterable<MyUser> {
        return userRepo.findAll()
    }

}

