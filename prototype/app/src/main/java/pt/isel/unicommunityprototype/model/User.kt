package pt.isel.unicommunityprototype.model

abstract class User(
    val id: Int,
    val name: String,
    val email: String
)

class Student(id: Int, name: String, email: String): User(id, name, email)
class Teacher(id: Int, name: String, email: String): User(id, name, email)

