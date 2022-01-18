package app.postr

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager


@Configuration
@EnableWebSecurity
public class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Override
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            ?.antMatchers("/", "/home", "signup")?.permitAll()
            ?.anyRequest()?.authenticated()
            ?.and()
            ?.formLogin()
            ?.loginPage("/signin")
            ?.permitAll()
            ?.and()
            ?.logout()
            ?.permitAll()
    }

    @Bean
    override fun userDetailsService(): UserDetailsService? {
        val user: UserDetails = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }
}