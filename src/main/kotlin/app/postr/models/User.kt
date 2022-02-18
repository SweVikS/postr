package app.postr.models

import org.springframework.data.repository.CrudRepository
import javax.persistence.*
import javax.persistence.CascadeType.*

/**
 * Entity Model Class for defining and creating MyUser table in PostgreSQL database.
 */
@Entity
class MyUser(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val username: String? = null,

    val password: String? = null,

    val email: String? = null,

    @OneToOne(cascade = [(ALL)]) var profile: Profile,

    @OneToMany(cascade = [ALL], fetch = FetchType.EAGER, mappedBy = "user")
    var posts: MutableList<Post>
)

/**
 * Interface for implementing Spring Data CrudRepository functions on MyUser objects. Repository is link between backend and database.
 */
interface UserRepo : CrudRepository<MyUser, Long> {
    fun findByUsername(username: String): MyUser
    fun findByEmail(email: String?): MyUser


    override fun findAll(): MutableIterable<MyUser>

}
