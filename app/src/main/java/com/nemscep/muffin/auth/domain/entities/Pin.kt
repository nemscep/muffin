package com.nemscep.muffin.auth.domain.entities

/**
 * Domain class representing pin used for authentication by user.
 */
data class Pin(val value: Int)

fun Pin.toEntity() = value.toString()
fun String.toDomain() = Pin(value = toInt())
