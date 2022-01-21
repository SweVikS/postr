package app.postr.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Profile(@Id
              @GeneratedValue(strategy = GenerationType.AUTO)
              val id: Long? = null,
              val description: String? = null


)