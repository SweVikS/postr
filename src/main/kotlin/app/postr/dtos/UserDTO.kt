package app.postr.dtos

import app.postr.utils.validation.*
import javax.validation.constraints.*

@PasswordMatch
class UserDTO {

    @UniqueUsername
    @NotNull
    @Size(
        min = 3,
        max = 30,
        message = "Username must be between {min} and {max} characters long"
    )
    var username: String = ""

    @ValidEmail
    @UniqueEmail
    @NotNull
    @NotEmpty(message = "Email cannot be empty")
    var email: String = ""

    @ValidPassword
    @NotNull
    var password: String = ""

    @NotNull
    var matchingPassword: String = ""


}






