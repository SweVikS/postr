package app.postr

import app.postr.models.Role
import app.postr.models.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
                "/error",
                "/css/**",
                "/js/**",
                "/img/**"
            ).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/signin")
            .permitAll()
            .and()
            .logout()
            .permitAll()
            .logoutSuccessUrl("/home")


    }

    /**
     * Configuration Bean used by configure function to access the UserRepo.
     */

    @Autowired
    val userDetailsService: MyUserDetailsService = MyUserDetailsService(userRepo)

    /**
     * Configuration Bean used by configure function to handle password encryption.
     */
    @Bean
    fun passWordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
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
     * Throws UserNameNotFoundException if no User with given username present in database.
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

    /**
     * Defines list of User's Granted Authorities.
     */
    fun getAuthorities(roles: MutableCollection<Role>?): MutableList<GrantedAuthority>? {
        val authorities = mutableListOf<GrantedAuthority>()
        for (role: Role in roles!!) {
            authorities.add(SimpleGrantedAuthority(role.toString()))
        }
        return authorities
    }
}