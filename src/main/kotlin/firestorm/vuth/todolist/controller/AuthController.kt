package firestorm.vuth.todolist.controller

import firestorm.vuth.todolist.dto.request.LoginRequest
import firestorm.vuth.todolist.dto.request.RegisterRequest
import firestorm.vuth.todolist.dto.response.AuthResponse
import firestorm.vuth.todolist.security.JwtUtil
import firestorm.vuth.todolist.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
class AuthController(
    private val userService: UserService,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager,
) {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest) : ResponseEntity<Any> {
        return try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.email, request.password)
            )

            if (authentication.isAuthenticated) {
                val userDetails = authentication.principal as UserDetails
                val jwt = jwtUtil.generateToken(userDetails)
                return ResponseEntity.ok(AuthResponse(jwt))
            } else {
                throw BadCredentialsException("Invalid credentials")
            }
        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                mapOf(
                    "error" to ex.message,
                )
            )
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest) : ResponseEntity<Any> {
        try {
            userService.register(request)
            return ResponseEntity.status(HttpStatus.CREATED).body(
                mapOf(
                    "message" to "Registration successful",
                    "username" to request.name,
                )
            )
        } catch (ex: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                mapOf(
                    "error" to ex.message,
                )
            )
        }
    }
}