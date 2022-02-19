package app.postr

import app.postr.models.MyUser
import app.postr.models.Role
import app.postr.models.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
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
public abstract class WebSecurityConfig(@Autowired val userRepo: UserRepo) : WebSecurityConfigurerAdapter() {

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
//    @Bean
//    override fun userDetailsService(): UserDetailsService? {
//        return MyUserDetailsService(userRepo)
//    }

    @Autowired
    val userDetailsService : MyUserDetailsService = MyUserDetailsService(userRepo)

    @Autowired
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }

//    @Bean
//    @Throws(java.lang.Exception::class)
//    override fun authenticationManagerBean(): AuthenticationManager? {
//        return super.authenticationManagerBean()
//    }


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
class MyUserDetailsService(val userRepository: UserRepo) : UserDetailsService {

    /**
     * Sends parameter username to UserRepo.findByUsername function,
     * retrieves a User object and returns it
     * encapsulated in a MyUserDetails object (the principal).
     */
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)

        var enabled: Boolean = true
        var accountNonExpired: Boolean = true
        var credentialsNonExpired: Boolean = true
        var accountNonLocked: Boolean = true

        return User(
            user.email, user.password, enabled, accountNonExpired,
            credentialsNonExpired, accountNonLocked, getAuthorities(user.roles)
        )
    }

    fun getAuthorities(roles: MutableCollection<Role>?) : MutableList<out GrantedAuthority>{
 val authorities = mutableListOf<GrantedAuthority>()
 for(role : Role in roles!!){
     authorities.add(SimpleGrantedAuthority(role.toString()))
 }
        return authorities
    }


}

/**
 * Contains function override of functions in UserDetails interface.
 * This object is the Principal instance of the
 * currently logged in user, encapsulated in an Authentication object.
 */
//class MyUserDetails(val user: MyUser) : UserDetails {
//
//    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
//        return HashSet()
//    }
//
//    override fun getPassword(): String {
//        return user.password!!
//    }
//
//    override fun getUsername(): String {
//        return user.username!!
//    }
//
//    override fun isAccountNonExpired(): Boolean {
//        return true
//    }
//
//    override fun isAccountNonLocked(): Boolean {
//        return true
//    }
//
//    override fun isCredentialsNonExpired(): Boolean {
//        return true
//    }
//
//    override fun isEnabled(): Boolean {
//        return true
//    }
//}