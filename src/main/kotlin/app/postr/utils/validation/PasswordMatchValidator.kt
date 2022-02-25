package app.postr.utils.validation


import app.postr.dtos.SignupDTO
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass


class PasswordMatchValidator : ConstraintValidator<PasswordMatch, SignupDTO> {

    override fun initialize(constraintAnnotation: PasswordMatch) {}

    override fun isValid(signupDTO: SignupDTO, context: ConstraintValidatorContext): Boolean {
        return signupDTO.password == signupDTO.matchingPassword
    }
}

@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PasswordMatchValidator::class])
annotation class PasswordMatch(
    val message: String = "Passwords do not match",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)











