package app.postr.models

import org.springframework.data.repository.CrudRepository
import javax.persistence.*
import javax.persistence.CascadeType.*


@Entity
class MyUser(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    @Column(nullable = false, unique = true)
    val username: String? = null,
    val password: String? = null,
    @OneToOne(cascade = [(CascadeType.ALL)]) var profile: Profile
)

//hibernate
interface UserRepo : CrudRepository<MyUser, Long> {
    fun findByUsername(username: String): MyUser

}
