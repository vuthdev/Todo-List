package firestorm.vuth.todolist.service.Impl

import firestorm.vuth.todolist.dto.request.RegisterRequest
import firestorm.vuth.todolist.exception.UserExistException
import firestorm.vuth.todolist.exception.UserNotFoundException
import firestorm.vuth.todolist.model.User
import firestorm.vuth.todolist.model.UserRole
import firestorm.vuth.todolist.repository.UserRepo
import firestorm.vuth.todolist.security.JwtFilter
import firestorm.vuth.todolist.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepo: UserRepo,
    private val passwordEncoder: PasswordEncoder,
): UserService {
    override fun register(request: RegisterRequest): User {
        if (userRepo.existsByName(request.name)) {
            throw UserExistException("Username ${request.name} exists")
        }

        if (userRepo.existsByEmail(request.email)) {
            throw UserExistException("Email ${request.email} exists")
        }

        val user = User(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            roles = mutableSetOf(UserRole.ROLE_USER)
        )

        return userRepo.save(user)
    }

    override fun delete(user: User) {
        userRepo.delete(user)
    }

    override fun findByName(name: String): User? {
        return userRepo.findByName(name.trim().lowercase())
    }

    override fun findByEmail(email: String): User? {
        return userRepo.findByName(email.trim().lowercase())
    }

    override fun loadUserByUsername(email: String): UserDetails {
        return userRepo.findByEmail(email.trim().lowercase()) ?: throw UserNotFoundException("User not found")
    }
}