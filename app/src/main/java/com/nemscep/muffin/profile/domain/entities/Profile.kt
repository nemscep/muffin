package com.nemscep.muffin.profile.domain.entities

/**
 * Domain class representing user profile info.
 */
data class Profile(
    val name: String,
    val monthlyIncome: Int,
    val currency: Currency,
    val pin: Int
)

enum class Currency {
    EUR,
    US_DOLLAR,
    SWISS_FRANC,
    RSD
}
