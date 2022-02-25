package app.postr.utils.validation

import org.passay.*
import java.util.*
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

class ValidPasswordValidator : ConstraintValidator<ValidPassword, String> {

    val passayValidator: PasswordValidator = PasswordValidator(
        LengthRule(8, 30),
        CharacterRule(EnglishCharacterData.UpperCase, 1),
        CharacterRule(EnglishCharacterData.LowerCase, 1),
        CharacterRule(EnglishCharacterData.Digit, 1),
        CharacterRule(EnglishCharacterData.Special, 1),
        WhitespaceRule()
    )

    override fun initialize(constraintAnnotation: ValidPassword) {}

    override fun isValid(password: String, context: ConstraintValidatorContext): Boolean {

        val result: RuleResult = passayValidator.validate(PasswordData(password))
        if (result.isValid) {
            return true
        }

        val msg = passayValidator.getMessages(result).joinToString().replace(".", "")

        context.disableDefaultConstraintViolation()
        context.buildConstraintViolationWithTemplate(msg)
            .addConstraintViolation()
        return false
    }
}

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidPasswordValidator::class])
annotation class ValidPassword(
    val message: String = "Invalid password",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
