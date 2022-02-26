package app.postr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

/**
 * Annotation "exclude" is set to exclude Spring Security Autoconfiguration, enabling custom implementation.
 */
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class PostrApplication

fun main(args: Array<String>) {
	runApplication<PostrApplication>(*args)
}
