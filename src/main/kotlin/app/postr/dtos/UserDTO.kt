package app.postr.dtos

import app.postr.utils.validation.PasswordMatch
import app.postr.utils.validation.UniqueEmail
import app.postr.utils.validation.UniqueUsername
import app.postr.utils.validation.ValidEmail
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@PasswordMatch
class UserDTO {

    @UniqueUsername
    @NotNull
    @NotEmpty(message = "Username cannot be empty")
    var username: String = ""

    @ValidEmail
    @UniqueEmail
    @NotNull
    @NotEmpty(message = "Email cannot be empty")
    var email: String = ""

    @NotNull
    @NotEmpty(message = "Password cannot be empty")
    var password: String = ""

    @NotNull
    @NotEmpty(message = "Password cannot be empty")
    var matchingPassword: String = ""


}






