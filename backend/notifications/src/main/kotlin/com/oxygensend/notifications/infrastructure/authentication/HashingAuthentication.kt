package com.oxygensend.notifications.infrastructure.authentication

import com.oxygensend.notifications.context.authentication.AuthException
import com.oxygensend.notifications.context.authentication.Authentication
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

@Component
internal class HashingAuthentication : Authentication {
    @Throws(AuthException::class)
    override fun authenticate(params: Map<String, String?>) {
        val plainPassword = params["password"] ?: throw IllegalArgumentException("Password is required")
        val hashedPassword = params["hashedPassword"]
            ?: throw IllegalArgumentException("Hashed password is required")
        val hash = calculateHash(plainPassword)
        val hashPassword = HexFormat.of().parseHex(hashedPassword)
        if (!MessageDigest.isEqual(hash, hashPassword)) {
            throw AuthException("Invalid password")
        }
    }

    @Throws(AuthException::class)
    private fun calculateHash(plainPassword: String): ByteArray {
        return try {
            val md = MessageDigest.getInstance("SHA-256")
            val passwordBytes = plainPassword.toByteArray(ENCODING)
            val first = md.digest(passwordBytes)
            md.digest(first)
        } catch (e: NoSuchAlgorithmException) {
            throw AuthException(e.message)
        }
    }

    companion object {
        private val ENCODING = StandardCharsets.UTF_8
    }
}
