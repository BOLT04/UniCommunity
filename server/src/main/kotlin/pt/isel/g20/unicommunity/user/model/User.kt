package pt.isel.g20.unicommunity.user.model

import javax.persistence.*

@Entity(name = "users")
open class User(
        @Column(nullable = false)
        var name: String,

        @Column(nullable = false, unique = true)
        var email: String,

        @Column(nullable = false)
        var pw: String,

        @Column(nullable = false)
        var role: String,

        @Column var githubId: String? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    constructor() : this("", "", "", "", null)
    constructor(
            id: Long,
            name: String,
            email: String,
            pw: String,
            role: String,
            githubId: String?
    ) : this(name, email, pw, role, githubId){
        this.id = id
    }
}
