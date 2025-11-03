package com.getreconnected.reconnected.core.dataManager

import android.content.Context
import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.getreconnected.reconnected.core.AppUsageRepository
import com.getreconnected.reconnected.core.Chatbot
import com.getreconnected.reconnected.core.models.entities.AppUsageInfo
import com.getreconnected.reconnected.core.util.formatTime
import com.getreconnected.reconnected.core.util.getDaysActive
import com.getreconnected.reconnected.core.util.getScreenTimeInMillis
import com.google.firebase.Firebase
import com.google.firebase.ai.Chat
import com.google.firebase.ai.GenerativeModel
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.HarmBlockThreshold
import com.google.firebase.ai.type.HarmCategory
import com.google.firebase.ai.type.SafetySetting
import com.google.firebase.ai.type.content

object ChatManager {
    var model: GenerativeModel? = null

    /**
     * Initiates a chat session with a predefined conversation starter.
     *
     * @param name The name of the user starting the chat. This will be included in the initial message.
     */
    fun startChat(
        name: String?,
        appUsageBreakdown: List<AppUsageInfo>,
        context: Context,
    ): Chat {
        val initialPrompt = generateInitialPrompt(name, appUsageBreakdown, context)
        model =
            Firebase
                .ai(backend = GenerativeBackend.Companion.googleAI())
                .generativeModel(
                    modelName = Chatbot.MODEL,
                    systemInstruction = content { text(initialPrompt) },
                    safetySettings =
                        listOf(
                            SafetySetting(
                                HarmCategory.Companion.HARASSMENT,
                                HarmBlockThreshold.Companion.ONLY_HIGH,
                            ),
                            SafetySetting(
                                HarmCategory.Companion.DANGEROUS_CONTENT,
                                HarmBlockThreshold.Companion.ONLY_HIGH,
                            ),
                            SafetySetting(
                                HarmCategory.Companion.HATE_SPEECH,
                                HarmBlockThreshold.Companion.ONLY_HIGH,
                            ),
                            SafetySetting(
                                HarmCategory.Companion.SEXUALLY_EXPLICIT,
                                HarmBlockThreshold.Companion.ONLY_HIGH,
                            ),
                        ),
                )
        if (model == null) {
            throw Exception("Model is null")
        }

        return model!!.startChat(
            history =
                listOf(
                    content(role = "model") { text(Chatbot.INITIAL_RESPONSE) },
                ),
        )
    }

    /**
     * Generates a response by sending the given prompt to a generative model.
     *
     * @param prompt The input string used to generate a response.
     * @return A Chat object containing the generated response text and additional metadata.
     */
    suspend fun getResponse(prompt: String): com.getreconnected.reconnected.core.models.entities.Chat {
        try {
            if (model == null) {
                throw Exception("Model is null")
            }
            val response = model!!.generateContent(prompt)
            return com.getreconnected.reconnected.core.models.entities.Chat(
                prompt = response.text ?: "error",
                bitmap = null,
                isFromUser = false,
            )
        } catch (e: Exception) {
            return com.getreconnected.reconnected.core.models.entities.Chat(
                prompt = e.message ?: "error",
                bitmap = null,
                isFromUser = false,
            )
        }
    }

    /**
     * Generates an initial chatbot prompt customized with the user's information.
     *
     * @param name The name of the user to be included in the generated prompt.
     * @return A string containing the initial chatbot prompt with user-specific details and screen time breakdown.
     */
    fun generateInitialPrompt(
        name: String?,
        appUsageBreakdown: List<AppUsageInfo>,
        context: Context,
    ): String {
        // TODO: Integrate the data aggregation system to get this information
        val daysSinceStarted = getDaysActive(context = context)
        val screenTimeTotal = formatTime(getScreenTimeInMillis(context = context))

        var prompt =
            Chatbot.INITIAL_PROMPT +
                """
            |
            |The user has this information:
            |- Name: ${name ?: "Unable to retrieve name"}
            |- Days since started: $daysSinceStarted
            |
            |The user's screen time for today is $screenTimeTotal. This is a breakdown of the total screen time:
                """.trimMargin()

        Log.d("ChatManager", "There are ${appUsageBreakdown.size} apps detected.")
        for (app in appUsageBreakdown) {
            prompt += "\n- \"${app.appName}\": ${formatTime(app.usageTime)}"
        }

        Log.d("ChatManager", "generateInitialPrompt: $prompt")
        return prompt
    }
}
