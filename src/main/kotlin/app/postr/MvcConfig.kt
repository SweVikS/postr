package app.postr

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MvcConfig :  WebMvcConfigurer{
    override fun addViewControllers(registry: ViewControllerRegistry){
        registry.addViewController("/").setViewName("home")
        registry.addViewController("/home").setViewName("home")
        registry.addViewController("/signin").setViewName("signin")
        registry.addViewController("/signup").setViewName("signup")
        registry.addViewController("/timeline").setViewName("timeline")

    }
}