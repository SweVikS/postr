package app.postr.models

import org.springframework.data.repository.CrudRepository
import javax.persistence.*

/**
 * Entity Model Class for defining and creating Post table in PostgreSQL database.
 */
@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    var heading: String? = null,
    var body: String? = null,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: MyUser? = null
)

/**
 * Interface for implementing Spring Data CrudRepository functions on Post objects. Repository is link between backend and database.
 */
interface PostRepo : CrudRepository<Post, Long> {
    override fun findAll(): MutableIterable<Post>
}
