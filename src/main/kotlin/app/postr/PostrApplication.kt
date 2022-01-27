package app.postr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("app.postr.controllers")
@ComponentScan("app.postr.services")
@ComponentScan("app.postr")
class PostrApplication

fun main(args: Array<String>) {
	runApplication<PostrApplication>(*args)
}
