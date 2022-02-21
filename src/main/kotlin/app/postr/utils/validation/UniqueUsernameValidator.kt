package app.postr.utils.validation

import app.postr.models.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

class UniqueUsernameValidator(@Autowired val userRepo: UserRepo) : ConstraintValidator<UniqueUsername, String> {

    override fun initialize(constraintAnnotation: UniqueUsername) {}

    override fun isValid(username: String, context: ConstraintValidatorContext?): Boolean {

        var isUnique: Boolean = false
        try {
            userRepo.findByUsername(username)
        } catch (e: EmptyResultDataAccessException) {
            isUnique = true
        }
        return isUnique
    }
}



@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueUsernameValidator::class])
annotation class UniqueUsername(
    val message: String = "This username already exists",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)