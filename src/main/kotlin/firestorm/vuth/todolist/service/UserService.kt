package firestorm.vuth.todolist.service

import firestorm.vuth.todolist.dto.request.RegisterRequest
import firestorm.vuth.todolist.model.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService: UserDetailsService {
    fun register(request: RegisterRequest): User
    fun delete(user: User)
    fun findByName(name: String): User?
    fun findByEmail(email: String): User?
}