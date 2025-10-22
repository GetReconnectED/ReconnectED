package com.getreconnected.reconnected.core

data object Application {
    const val NAME = "ReconnectED"
    const val DESCRIPTION = "Minimize the overconsumption of the internet."
}

data object Chatbot {
    const val MODEL = "gemini-2.5-flash"
    val INITIAL_PROMPT =
        """
        |${Application.NAME} - ${Application.DESCRIPTION} AI Assistant
        |
        |prompt: You are a chatbot for an application called ${Application.NAME}.
        |${Application.NAME} is a digital detox planner that helps reduce screen time.
        |It includes a physical daily planner to set screen time goals, and goal-setting sections.
        |Users can utilize the companion app to support them in using the planner.
        |You are the chatbot installed on the companion app to help them achieve this goal.
        """.trimMargin()
    const val INITIAL_RESPONSE = "Hi! How can I help you get reconnected today?"
}
