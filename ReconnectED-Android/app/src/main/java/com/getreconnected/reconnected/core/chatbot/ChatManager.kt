package com.getreconnected.reconnected.core.chatbot

import com.getreconnected.reconnected.core.models.Chat
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content

object ChatManager {
    val model =
        Firebase
            .ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.5-flash")

    /**
     * Initiates a chat session with a predefined conversation starter.
     *
     * @param name The name of the user starting the chat. This will be included in the initial message.
     */
    fun startChat(name: String) =
        model.startChat(
            history =
                listOf(
                    content(role = "user") { text("Hello, I am $name.") },
                    content(role = "model") { text("Hi! How can I help you get reconnected today?") },
                ),
        )

    /**
     * Generates a response by sending the given prompt to a generative model.
     *
     * @param prompt The input string used to generate a response.
     * @return A Chat object containing the generated response text and additional metadata.
     */
    suspend fun getResponse(prompt: String): Chat {
        try {
            val response = model.generateContent(prompt)
            return Chat(
                prompt = response.text ?: "error",
                bitmap = null,
                isFromUser = false,
            )
        } catch (e: Exception) {
            return Chat(
                prompt = e.message ?: "error",
                bitmap = null,
                isFromUser = false,
            )
        }
    }
}
