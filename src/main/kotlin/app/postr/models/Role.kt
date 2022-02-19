package app.postr.models

import org.springframework.data.repository.CrudRepository
import javax.persistence.*


@Entity
class Role(roleName: String?, privileges: MutableCollection<Privilege>?) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private val id: Long? = null
    private var name: String? = roleName

    @ManyToMany(mappedBy = "roles")
    private var users: Collection<MyUser>? = null

    @ManyToMany
    @JoinTable(
        name = "roles_privileges",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")]
    )
    private var privileges: MutableCollection<Privilege?>? = null
}
interface RoleRepo : CrudRepository<Role, Long> {
    fun findByName(name: String?) : Role?
}