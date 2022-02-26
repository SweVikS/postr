package app.postr

import app.postr.models.MyUser
import app.postr.models.Role
import app.postr.models.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.LocaleResolver
import java.io.IOException
import java.util.*
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * Class for handling Authentication and Authorization.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig(
    @Autowired val userRepo: UserRepo
) : WebSecurityConfigurerAdapter() {

    /**
     * Intercepts all requests and redirects according to authentication status. Recieves Authentication object containing
     * User Credentials if user is not logged in. When user is logged in, Authentication object containing Principal is sent
     * to ThreadLocal. Principal is used to identify logged in User.
     */
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers(
                "/",
                "/home",
                "/signup",
                "/signin",
                "/css/**",
                "/js/**",
                "/img/**",
                "/h2-console/**"
            ).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/signin")
//            .failureUrl("")
            .permitAll()
            .and()
            .logout()
            .permitAll()
            .logoutSuccessUrl("/home")


    }

//    @Component
//    class LoginFailureHandler(
//    ) : SimpleUrlAuthenticationFailureHandler() {
//
//        @Autowired
//        private val messages: MessageSource? = null
//
//        @Autowired
//        private val localeResolver: LocaleResolver? = null
//
//
//        override fun onAuthenticationFailure(
//            request: HttpServletRequest,
//            response: HttpServletResponse?,
//            exception: AuthenticationException?
//        ) {
//            setDefaultFailureUrl("/signin-failure")
//            super.onAuthenticationFailure(request, response, exception)
//
//            var locale: Locale = localeResolver!!.resolveLocale(request)
//            var errMsg: String = messages!!.getMessage("message.badCredentials", null, locale)
//
//            request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errMsg)
//        }
//    }

    /**
     * Configuration Bean used by configure function to access the UserRepo.
     */

    @Autowired
    val userDetailsService: MyUserDetailsService = MyUserDetailsService(userRepo)

    @Autowired
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }

    /**
     * Configuration Bean used by configure function to handle password encryption.
     */
    @Bean
    fun passWordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

//    @Bean
//    fun authFailureHandler(): SimpleUrlAuthenticationFailureHandler {
//        return LoginFailureHandler()
//    }
}

/**
 * Contains function override of loadUserByUsername from interface UserDetailsService. Has UserRepo
 * autowired into class for use in loadUserByUserName function.
 *
 */
@Service
@Transactional
class MyUserDetailsService(
    @Autowired
    val userRepository: UserRepo
) : UserDetailsService {

    /**
     * Sends parameter username to UserRepo.findByUsername function,
     * retrieves a User object and returns it
     * encapsulated in a MyUserDetails object (the principal).
     */
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {

        val enabled = true
        val accountNonExpired = true
        val credentialsNonExpired = true
        val accountNonLocked = true


        val user = userRepository.findByUsername(username);
        if (!user?.username.equals(username)) {
            throw UsernameNotFoundException(username);
        }
        return User(
            user?.username, user?.password, enabled, accountNonExpired,
            credentialsNonExpired, accountNonLocked, getAuthorities(user?.roles)
        )
    }

    fun getAuthorities(roles: MutableCollection<Role>?): MutableList<GrantedAuthority>? {
        val authorities = mutableListOf<GrantedAuthority>()
        for (role: Role in roles!!) {
            authorities.add(SimpleGrantedAuthority(role.toString()))
        }
        return authorities
    }
}