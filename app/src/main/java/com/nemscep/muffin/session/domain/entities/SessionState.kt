package com.nemscep.muffin.session.domain.entities

/**
 * Represents application's session state.
 */
sealed class SessionState {
    /**
     * Represents state where user is eligible to use the app and is using it.
     * Once he stops using the app, after some delay, his permission is revoked.
     */
    object InProgress : SessionState()

    /**
     * Represents state where user is not eligible to use the app anymore and requires him to restart it.
     */
    object Expired : SessionState()
}
