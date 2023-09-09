package com.swanand.mc.database

import java.util.UUID

object SessionManager {
    private var sessionId: String? = null

    fun getSessionId(): String {
        if (sessionId == null) {
            sessionId = generateUniqueSessionId()
        }
        return sessionId!!
    }

    private fun generateUniqueSessionId(): String {
        // Implement your logic to generate a unique session ID here
        return UUID.randomUUID().toString()
    }

    fun clearSession() {
        sessionId = null
    }
}
