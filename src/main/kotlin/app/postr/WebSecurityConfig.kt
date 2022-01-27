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


@Configuration
@EnableWebSecurity
public class WebSecurityConfig(@Autowired val userRepo : UserRepo) : WebSecurityConfigurerAdapter() {

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

    @Bean
    override fun userDetailsService(): UserDetailsService? {

        return MyUserDetailsService(userRepo)
    }

    @Bean
    fun passWordEncoder() : PasswordEncoder{
        return BCryptPasswordEncoder()
    }
}


class MyUserDetailsService(val userRepository: UserRepo) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException(username)
        return MyUserDetails(user)
    }
}

//user credentials object
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

    fun setProfileDescription(description : String) {
        user.profile?.description = description
    }

    fun getProfileDescription() : String? {
        return user.profile?.description
    }
}