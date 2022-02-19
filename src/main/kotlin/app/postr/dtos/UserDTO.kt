package app.postr.dtos

import app.postr.utils.validation.PasswordMatch
import app.postr.utils.validation.ValidEmail
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@PasswordMatch
class UserDTO {

    @NotNull
    @NotEmpty
    var username: String = ""

    @ValidEmail
    @NotNull
    @NotEmpty
    var email: String = ""

    @NotNull
    @NotEmpty
    var password: String = ""

    @NotNull
    @NotEmpty
    var matchingPassword: String = ""


}






