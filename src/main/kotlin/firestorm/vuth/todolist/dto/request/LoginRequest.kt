package firestorm.vuth.todolist.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:Email
    @field:NotBlank("Email cannot be blank")
    val email: String,

    @field:NotBlank("Password cannot be blank")
    val password: String
)
