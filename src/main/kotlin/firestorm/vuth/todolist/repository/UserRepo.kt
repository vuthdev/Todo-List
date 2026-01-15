package firestorm.vuth.todolist.repository

import firestorm.vuth.todolist.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepo: JpaRepository<User, Long> {
    fun findByName(name: String): User?
    fun findByEmail(email: String): User?
    fun existsByName(name: String): Boolean
    fun existsByEmail(email: String): Boolean
}