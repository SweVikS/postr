package app.postr.utils.validation

import app.postr.models.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass


class UniqueEmailValidator(@Autowired val userRepo: UserRepo) : ConstraintValidator<UniqueEmail, String> {

    override fun initialize(constraintAnnotation: UniqueEmail) {}

    override fun isValid(email: String?, context: ConstraintValidatorContext?): Boolean {

        return userRepo.findByEmail(email) == null
    }
}

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueEmailValidator::class])
annotation class UniqueEmail(
    val message: String = "This email already exists",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)