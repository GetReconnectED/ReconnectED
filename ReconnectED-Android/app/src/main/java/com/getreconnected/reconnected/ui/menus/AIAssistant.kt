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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.background(Color.White).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier.weight(1f).height(50.dp),
                    placeholder = { Text("Type a message...") },
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { /* send message logic */ },
                    modifier = Modifier.height(50.dp),
                ) {
                    Text("Send")
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
                    ).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ChatroomScreen()
        }
    }
}

/**
 * A composable representing the main chatroom screen interface.
 */
@Composable
@Suppress("ktlint:standard:function-naming")
fun ChatroomScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
    ) {
        ChatBubble(
            avatarRes = R.drawable.gemini_logo,
            name = "Gemini AI",
            message = "How can I help you?",
        )
        Spacer(modifier = Modifier.height(12.dp))
        UserBubble(message = "Give me a summary of what is my most used application")
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
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painterResource(id = avatarRes),
            contentDescription = "Avatar",
            modifier = Modifier.size(40.dp).clip(CircleShape),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
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
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = Color(0xFF6FD1B4),
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
