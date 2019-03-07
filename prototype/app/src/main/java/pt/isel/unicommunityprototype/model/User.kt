package pt.isel.unicommunityprototype.model

sealed class User(
    val id: Int,
    val name: String,
    val email: String,
    val boards: List<Board> // The list of boards the user is associated/subscribed to
)

class Student(id: Int, name: String, email: String, boards: List<Board>): User(id, name, email, boards)
class Teacher(id: Int, name: String, email: String, boards: List<Board>): User(id, name, email, boards)

