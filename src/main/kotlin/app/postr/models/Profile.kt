package app.postr.models

import org.springframework.data.repository.CrudRepository
import javax.persistence.*

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


interface ProfileRepo : CrudRepository<Profile, Long> {
    fun findById(id: String): Profile?
}
