package app.postr.models

import org.springframework.data.repository.CrudRepository
import javax.persistence.*

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


interface PostRepo : CrudRepository<Post, Long> {
    override fun findAll(): MutableIterable<Post>
}
