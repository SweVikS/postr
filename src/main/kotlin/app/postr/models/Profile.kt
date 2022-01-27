package app.postr.models

import org.springframework.data.repository.CrudRepository
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Profile(@Id
              @GeneratedValue(strategy = GenerationType.AUTO)
              val id: Long? = null,
              var description: String? = null)

interface ProfileRepo : CrudRepository<Profile, Long> {
    fun findById(id: String) : Profile?}
