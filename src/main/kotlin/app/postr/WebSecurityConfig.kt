package app.postr

import app.postr.models.MyUser
import app.postr.models.Profile
import app.postr.models.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.stereotype.Service

/**
 * Class for handling Authentication and Authorization.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig(@Autowired val userRepo : UserRepo) : WebSecurityConfigurerAdapter() {

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
            ).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/signin")
            .failureUrl("/signin-failure")
            .permitAll()
            .and()
            .logout()
            .permitAll()
            .logoutSuccessUrl("/home");
    }

/**
 * Configuration Bean used by configure function to access the UserRepo.
 */
    @Bean
    override fun userDetailsService(): UserDetailsService? {
        return MyUserDetailsService(userRepo)
    }

/**
 * Configuration Bean used by configure function to handle password encryption.
 */
    @Bean
    fun passWordEncoder(): PasswordEncoder{
        return BCryptPasswordEncoder()
    }
}

/**
 * Contains function override of loadUserByUsername from interface UserDetailsService. Has UserRepo
 * autowired into class for use in loadUserByUserName function.
 *
 */
class MyUserDetailsService(val userRepository: UserRepo) : UserDetailsService {

/**
 * Sends parameter username to UserRepo.findByUsername function,
 * retrieves a User object and returns it
 * encapsulated in a MyUserDetails object (the principal).
 */
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException(username)
        return MyUserDetails(user)
    }
}

/**
 * Contains function override of functions in UserDetails interface.
 * This object is the Principal instance of the
 * currently logged in user, encapsulated in an Authentication object.
 */
class MyUserDetails( val user: MyUser) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return HashSet()
    }

    override fun getPassword(): String {
        return user.password!!
    }

    override fun getUsername(): String {
        return user.username!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}