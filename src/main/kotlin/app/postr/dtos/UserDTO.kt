package app.postr.dtos

import app.postr.utils.validation.PasswordMatch
import app.postr.utils.validation.ValidEmail
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@PasswordMatch
class UserDTO {

    @NotNull
    @NotEmpty
    val username: String=""

    @NotNull
    @NotEmpty
    val password: String=""

    @NotNull
    @NotEmpty
    val matchingPassword: String=""

    @ValidEmail
    @NotNull
    @NotEmpty
    val email: String=""
}






