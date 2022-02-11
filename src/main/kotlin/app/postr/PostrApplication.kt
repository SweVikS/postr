package app.postr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PostrApplication

fun main(args: Array<String>) {
	runApplication<PostrApplication>(*args)
}
