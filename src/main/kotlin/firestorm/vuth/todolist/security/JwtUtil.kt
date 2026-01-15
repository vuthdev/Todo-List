package firestorm.vuth.todolist.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey
import kotlin.io.encoding.Base64

@Component
class JwtUtil(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expires_in}") private val expiresIn: Int,
) {
    fun generateToken(userDetails: UserDetails): String {
        val now = Date()

        return Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .add("email", userDetails.username)
            .issuedAt(now)
            .expiration(Date(now.time + expiresIn))
            .and()
            .signWith(getKey())
            .compact()
    }
    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getKey(): SecretKey {
        val keyByte: String = Base64.encode(secret.toByteArray())
        return Keys.hmacShaKeyFor(keyByte.toByteArray())
    }

    fun extractEmail(token: String): String {
        return extractAllClaim(token).subject
    }

    fun extractAllClaim(token: String): Claims {
        return Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }
}