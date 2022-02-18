package app.postr.models

import org.springframework.data.repository.CrudRepository
import javax.persistence.*

@Entity
class Privilege(privName: String?) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

    var name: String? = privName
    @ManyToMany(mappedBy = "privileges")
    var roles: Collection<Role>?=null
}
interface PrivilegeRepo : CrudRepository<Privilege, Long> {
    fun findByName(name :String?) : Privilege?
}