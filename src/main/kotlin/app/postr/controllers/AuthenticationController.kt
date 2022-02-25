package app.postr.controllers

import app.postr.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.web.WebAttributes
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.naming.AuthenticationException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession


/**
 * Handles User signing in/signing up processes. UserService is autowired into the class for handling
 * saving new User in signupPost Controller.
 */
@Controller
class AuthenticationController(
) {


}



