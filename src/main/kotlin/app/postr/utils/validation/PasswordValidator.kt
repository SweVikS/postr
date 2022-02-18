package app.postr.utils.validation


import app.postr.dtos.UserDTO
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass


class PasswordValidator : ConstraintValidator<PasswordMatch, Object> {

    override fun initialize(constraintAnnotation: PasswordMatch) {}

    override fun isValid(ob: Object, context: ConstraintValidatorContext): Boolean {
        var user: UserDTO = ob as UserDTO
        return user.password.equals(user.matchingPassword)
    }
}


@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordValidator::class])
annotation class PasswordMatch(
    val message: String = "Passwords don't match",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)











