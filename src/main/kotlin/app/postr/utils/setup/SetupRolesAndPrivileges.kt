package app.postr.utils.setup

import app.postr.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import javax.transaction.Transactional

/**
 * Creates User Roles and Privileges if not present in database.
 */
@Component
class SetupRolesAndPrivileges(
    @Autowired
    val roleRepo: RoleRepo,
    @Autowired
    val privilegeRepo: PrivilegeRepo,
) : ApplicationListener<ContextRefreshedEvent?> {

    var alreadySetup = false


    @Transactional
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if (alreadySetup) return

        val readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE")
        val writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE")
        val userPrivileges = arrayListOf<Privilege>(readPrivilege, writePrivilege)
        createRoleIfNotFound("ROLE_USER", userPrivileges)
        alreadySetup = true
    }

    @Transactional
    fun createPrivilegeIfNotFound(privName: String?): Privilege {
        return privilegeRepo.findByName(privName) ?: privilegeRepo.save(Privilege(privName))

    }

    @Transactional
    fun createRoleIfNotFound(
        roleName: String?,
        privileges: MutableCollection<Privilege>?
    ): Role? {
        return roleRepo.findByName(roleName) ?: roleRepo.save(Role(roleName, privileges))
    }

}