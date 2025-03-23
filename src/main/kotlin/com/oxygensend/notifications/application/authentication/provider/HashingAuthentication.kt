package com.oxygensend.notifications.application.authentication.provider

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
        val hashingAlgorithm =
            params["hashingAlgorithm"] ?: throw IllegalArgumentException("Hashing Algorithm is required")
        val hash = calculateHash(plainPassword, hashingAlgorithm)
        var x = hash.toString();
        val hashPassword = HexFormat.of().parseHex(hashedPassword)
        if (!MessageDigest.isEqual(hash, hashPassword)) {
            throw AuthException("Invalid password")
        }
    }

    @Throws(AuthException::class)
    private fun calculateHash(plainPassword: String, hashAlgorithm: String): ByteArray {
        return try {
            val md = MessageDigest.getInstance(hashAlgorithm);
            val passwordBytes = plainPassword.toByteArray(ENCODING)
            md.digest(passwordBytes)
        } catch (e: NoSuchAlgorithmException) {
            throw AuthException(e.message)
        }
    }

    companion object {
        private val ENCODING = StandardCharsets.UTF_8
    }
}
