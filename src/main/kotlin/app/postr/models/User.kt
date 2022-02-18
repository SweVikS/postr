package app.postr.models

import org.springframework.data.repository.CrudRepository
import javax.persistence.*
import javax.persistence.CascadeType.*

/**
 * Entity Model Class for defining and creating MyUser table in PostgreSQL database.
 */
@Entity
class MyUser(
    username: String,
    password: String,
    email: String?,
    profile: Profile,
    posts: MutableList<Post>,
    roles: Collection<Role>
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

    @Column(nullable = false, unique = true)
    var username: String? = username

    var password: String? = password

    var email: String? = email

    @OneToOne(cascade = [(ALL)])
    var profile: Profile? = profile

    @OneToMany(cascade = [ALL], fetch = FetchType.EAGER, mappedBy = "user")
    var posts: MutableList<Post>? = posts

    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: Collection<Role>? = roles
}

/**
 * Interface for implementing Spring Data CrudRepository functions on MyUser objects. Repository is link between backend and database.
 */
interface UserRepo : CrudRepository<MyUser, Long> {
    fun findByUsername(username: String): MyUser
    fun findByEmail(email: String?): MyUser
    override fun findAll(): MutableIterable<MyUser>
}
