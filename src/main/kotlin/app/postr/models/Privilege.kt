package app.postr.models

import org.springframework.data.repository.CrudRepository
import javax.persistence.*

/**
 * Entity Model Class for defining and creating Privilege table in PostgreSQL database.
 */
@Entity
class Privilege(privName: String?) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null

    var name: String? = privName
    @ManyToMany(mappedBy = "privileges")
    var roles: Collection<Role>?=null
}

/**
 * Interface for implementing Spring Data CrudRepository functions on Privilege objects. Repository is link between backend and database.
 */
interface PrivilegeRepo : CrudRepository<Privilege, Long> {
    fun findByName(name :String?) : Privilege?
}