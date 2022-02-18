package app.postr.utils

import app.postr.models.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import javax.transaction.Transactional


@Component
class SetupRolesAndPrivileges(
    @Autowired
    val userRepo: UserRepo,
    @Autowired
    val roleRepo: RoleRepo,
    @Autowired
    val privilegeRepo: PrivilegeRepo
) : ApplicationListener<ContextRefreshedEvent?> {

    var alreadySetup = false




    @Transactional
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if (alreadySetup) return

        val readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE")
        val writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE")

        createRoleIfNotFound("ROLE_USER", listOf(readPrivilege,writePrivilege))
        alreadySetup = true
    }

    @Transactional
    fun createPrivilegeIfNotFound(privName: String?): Privilege {
        var newPrivilege: Privilege? = privilegeRepo.findByName(privName)
        if (newPrivilege == null) {
            newPrivilege = Privilege(privName)
            privilegeRepo.save(newPrivilege)
        }
        return newPrivilege
    }

    @Transactional
    fun createRoleIfNotFound(
        roleName: String?,
        privileges: Collection<Privilege>?
    ): Role? {
        var newRole: Role? = roleRepo.findByName(roleName)
        if (newRole == null) {
            newRole = Role(roleName,privileges)
            newRole
            roleRepo.save(newRole)
        }
        return newRole
    }

}