package pt.isel.g20.unicommunity.user.model

import javax.persistence.*

@Entity
class User(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var password: String,

    @Column var githubId: String? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    constructor() : this("", "", "", null)
}
