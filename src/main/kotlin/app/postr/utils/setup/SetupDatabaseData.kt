package app.postr.utils.setup

import app.postr.dtos.SignupDTO
import app.postr.services.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

/**
 * Populates database with data used for test purposes.
 * Repopulates database with data at every application start when using an InMemory database, such as H2.
 */
@Component
class SetupDatabaseData(
    @Autowired
    val profileService: ProfileService,
    @Autowired
    val userService: UserService,
    @Autowired
    val postService: PostService
) : ApplicationListener<ContextRefreshedEvent?> {

    var alreadySetup = false

    override fun onApplicationEvent(event: ContextRefreshedEvent) {

        if (alreadySetup) return


        try {
            SetupUsers()
            SetupProfiles()
            SetupPosts()
            alreadySetup = true
        } catch (ex: Exception) {
        }

    }

    fun SetupUsers() {
        val signupDTO1 = SignupDTO()
        signupDTO1.username = "Viktor"
        signupDTO1.email = "viktor@mail.se"
        signupDTO1.password = "Viktor1#"
        signupDTO1.matchingPassword = "Viktor1#"
        userService.registerNewUser(signupDTO1)

        val signupDTO2 = SignupDTO()
        signupDTO2.username = "Matilda Svensson"
        signupDTO2.email = "matilda@mail.se"
        signupDTO2.password = "tY4#gsH534!h"
        signupDTO2.matchingPassword = "tY4#gsH534!h"
        userService.registerNewUser(signupDTO2)

        val signupDTO3 = SignupDTO()
        signupDTO3.username = "coolingen_hehe"
        signupDTO3.email = "cool@mail.se"
        signupDTO3.password = "Cool123!"
        signupDTO3.matchingPassword = "Cool123!"
        userService.registerNewUser(signupDTO3)
    }

    fun SetupProfiles() {
        val profileDTO1 = ProfileDTO(
            "Mitt namn är Viktor. Jag programmerar.",
            "viktor@mail.se",
            "0702345431",
            "Vaktmästare"
        )
        profileService.saveProfile(profileDTO1, "Viktor")

        val profileDTO2 = ProfileDTO(
            "Matilda!!",
            "matilda@mail.se",
            "070234555",
            "Vaktmästare"
        )
        profileService.saveProfile(profileDTO2, "Matilda Svensson")

        val profileDTO3 = ProfileDTO(
            "Cool hehe",
            "cool@mail.se",
            "080234523",
            "Bossman"
        )
        profileService.saveProfile(profileDTO3, "coolingen_hehe")
    }

    fun SetupPosts() {
        val postDTO1 = PostDTO("Första posten!!!", "hahaha jag var först!!!")
        postService.savePost(postDTO1, "coolingen_hehe")

        val postDTO2 = PostDTO("WOW", "Postr verkar väldigt trevligt")
        postService.savePost(postDTO2, "Viktor")

        val postDTO3 = PostDTO("Kaffeautomaten är sönder", "Jag dricker bara te iofs, en headsup! ;)")
        postService.savePost(postDTO3, "Matilda Svensson")
    }
}



























