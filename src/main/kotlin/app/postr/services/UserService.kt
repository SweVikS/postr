package app.postr.services

import app.postr.models.MyUser
import app.postr.models.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired
                  val userRepo: UserRepo) {
private val bCryptEncoder = BCryptPasswordEncoder()

    fun saveNewUser(signupDTO: SignupDTO) {

        val encryptedPassword = bCryptEncoder.encode(signupDTO.password)
        val user = MyUser(username = signupDTO.username, password = encryptedPassword)
        userRepo.save(user)
    }
}

class SignupDTO(val username : String, val password : String)