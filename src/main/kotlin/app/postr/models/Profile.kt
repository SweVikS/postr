package app.postr.models

import org.springframework.data.repository.CrudRepository
import javax.persistence.*

/**
 * Entity Model Class for defining and creating Profile table in PostgreSQL database.
 */
@Entity
class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    var description: String? = null,
    var email: String? = null,
    var phoneNr: String? = null,
    var companyRole: String? = null
)

/**
 * Interface for implementing Spring Data CrudRepository functions on Profile objects. Repository is link between backend and database.
 */
interface ProfileRepo : CrudRepository<Profile, Long> {
    fun findById(id: String): Profile?
}
