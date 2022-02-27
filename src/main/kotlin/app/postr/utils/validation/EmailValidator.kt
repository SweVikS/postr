package app.postr.utils.validation

import java.lang.annotation.Documented
import java.util.regex.Pattern
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass


class EmailValidator : ConstraintValidator<ValidEmail, String> {

    val EMAIL_PATTERN: String =
        "^[_A-Za-z0-9-+]+" + "(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$"

    var pattern = Pattern.compile(this.EMAIL_PATTERN)


    override fun initialize(constraintAnnotation: ValidEmail) {}

    override fun isValid(email: String, context: ConstraintValidatorContext): Boolean {
        return pattern.matcher(email).matches()
    }


}
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [EmailValidator::class])
@Documented
annotation class ValidEmail(
    val message: String = "Invalid email",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)












