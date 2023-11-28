package endpoints.dto

import java.time.LocalDate

/**
 * This file contains all incoming DTOs.
 * Here, [LoginDto] is a data class containing immutable class members
 */
data class LoginDto(
    val mail: String,
    val password: String? = null,
    val code: String? = null
)

data class RegisterDto(
    val mail: String,
    val password: String,
)

data class CreateItemDto(
    val name: String,
    val count: Int,
    val note: String?,
)

data class UpdateItemDto(
    val id: Long,
    val name: String,
    val count: Int,
    val note: String?,
)

data class LocalDateRequest(
    val date: LocalDate
)
