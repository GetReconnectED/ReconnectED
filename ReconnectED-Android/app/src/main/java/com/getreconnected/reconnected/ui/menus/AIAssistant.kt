package com.getreconnected.reconnected.ui.menus

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.getreconnected.reconnected.R
import com.getreconnected.reconnected.core.Chatbot
import com.getreconnected.reconnected.core.auth.GoogleAuth
import com.getreconnected.reconnected.core.chatbot.ChatManager
import com.getreconnected.reconnected.core.models.Chat
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

/**
 * A composable function representing an AI-powered assistant chat interface.
 * It provides a scaffold with a chatroom screen and a bottom input bar for sending messages.
 *
 * @param modifier The modifier to be applied to the component, allowing layout and styling customization.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun AIAssistant(modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf("") }
    var chatHistory by remember {
        mutableStateOf(listOf(Chat(Chatbot.INITIAL_RESPONSE, null, false)))
    }
    var isLoading by remember { mutableStateOf(false) }
    var isValidInput by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val googleAuth = GoogleAuth()
    val chat =
        ChatManager.startChat(
            googleAuth.currentUser?.displayName,
        )

    // Auto-scroll to bottom when new messages are added
    LaunchedEffect(chatHistory.size) {
        if (chatHistory.isNotEmpty()) {
            listState.animateScrollToItem(chatHistory.size - 1)
        }
    }

    val sendMessage = {
        if (inputText.isNotBlank() && !isLoading) {
            val userMessage =
                Chat(
                    prompt = inputText,
                    bitmap = null,
                    isFromUser = true,
                )

            chatHistory = chatHistory + userMessage
            val currentInput = inputText
            inputText = ""
            isLoading = true

            coroutineScope.launch {
                try {
                    val aiResponse = chat.sendMessage(currentInput)
                    // Convert the AI response to a Chat object
                    val processedAiResponse =
                        Chat(
                            prompt = aiResponse.text ?: "",
                            bitmap = null,
                            isFromUser = false,
                        )
                    chatHistory = (chatHistory + processedAiResponse)
                } catch (e: Exception) {
                    val errorMessage =
                        Chat(
                            prompt = "Sorry, I encountered an error: ${e.message}",
                            bitmap = null,
                            isFromUser = false,
                        )
                    chatHistory = chatHistory + errorMessage
                } finally {
                    isLoading = false
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier =
                    Modifier
                        .background(Color.White)
                        .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextField(
                    value = inputText,
                    onValueChange = {
                        inputText = it
                        isValidInput = inputText.length > Chatbot.CHAT_MAX_LENGTH
                    },
                    modifier =
                        Modifier
                            .weight(1f)
                            .height(100.dp),
                    placeholder = { Text("Type a message...") },
                    enabled = !isLoading,
                    isError = isValidInput,
                    supportingText = { Text("${inputText.length} / ${Chatbot.CHAT_MAX_LENGTH}") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                        disabledContainerColor = Color(0xFFE0E0E0),
                        errorContainerColor = Color(0xFFFFE0E0)
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = sendMessage,
                    modifier = Modifier.height(50.dp),
                    enabled = inputText.isNotBlank() && !isLoading && inputText.length <= Chatbot.CHAT_MAX_LENGTH,
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White,
                        )
                    } else {
                        Text("Send")
                    }
                }
            }
        },
    ) { padding ->
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .background(
                        brush =
                            Brush.Companion.verticalGradient(
                                colors =
                                    listOf(
                                        Color(0xFFD1FAE5),
                                        Color(0xFFDBEAFE),
                                    ),
                            ),
                    ).padding(padding)
                    .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ChatroomScreen(
                chatHistory = chatHistory,
                listState = listState,
            )
        }
    }
}

/**
 * A composable representing the main chatroom screen interface.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun ChatroomScreen(
    chatHistory: List<Chat>,
    listState: androidx.compose.foundation.lazy.LazyListState,
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding =
            androidx.compose.foundation.layout
                .PaddingValues(vertical = 16.dp),
    ) {
        items(chatHistory) { chat ->
            if (chat.isFromUser) {
                UserBubble(message = chat.prompt)
            } else {
                ChatBubble(
                    avatarRes = R.drawable.gemini_logo,
                    name = "Gemini AI",
                    message = chat.prompt,
                )
            }
        }
    }
}

/**
 * Displays a chat bubble with an avatar, name, and message.
 *
 * @param avatarRes The resource ID of the avatar image displayed in the chat bubble.
 * @param name The name of the person associated with the chat message.
 * @param message The text content of the chat message.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun ChatBubble(
    avatarRes: Int,
    name: String,
    message: String,
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Image(
            painterResource(id = avatarRes),
            contentDescription = "Avatar",
            modifier = Modifier.size(40.dp).clip(CircleShape),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = Color.White,
                shadowElevation = 1.dp,
            ) {
                Text(
                    text = message,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 16.sp,
                )
            }
        }
    }
}

/**
 * A Composable that displays a user message bubble styled with a specific background color,
 * shape, and elevation.
 *
 * @param message The text content to be displayed within the user bubble.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun UserBubble(message: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = Color(0xFF6FD1B4),
            shadowElevation = 1.dp,
            modifier = Modifier.fillMaxWidth(0.8f), // Limit width to 80% of screen
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(10.dp),
                fontSize = 16.sp,
            )
        }
    }
}
