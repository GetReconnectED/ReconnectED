package com.getreconnected.reconnected.core.models

import android.graphics.Bitmap
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Unit tests for Chat data class
 */
class ChatTest {

    @Test
    fun chat_createsInstanceWithAllFields() {
        val prompt = "Hello, how are you?"
        val bitmap = mock(Bitmap::class.java)
        val isFromUser = true

        val chat = Chat(
            prompt = prompt,
            bitmap = bitmap,
            isFromUser = isFromUser
        )

        assertEquals(prompt, chat.prompt)
        assertEquals(bitmap, chat.bitmap)
        assertTrue(chat.isFromUser)
    }

    @Test
    fun chat_createsInstanceWithNullBitmap() {
        val prompt = "Test prompt"
        val isFromUser = false

        val chat = Chat(
            prompt = prompt,
            bitmap = null,
            isFromUser = isFromUser
        )

        assertEquals(prompt, chat.prompt)
        assertNull(chat.bitmap)
        assertFalse(chat.isFromUser)
    }

    @Test
    fun chat_createsUserMessage() {
        val userMessage = "User's message"

        val chat = Chat(
            prompt = userMessage,
            bitmap = null,
            isFromUser = true
        )

        assertEquals(userMessage, chat.prompt)
        assertTrue(chat.isFromUser)
    }

    @Test
    fun chat_createsSystemMessage() {
        val systemMessage = "System response"

        val chat = Chat(
            prompt = systemMessage,
            bitmap = null,
            isFromUser = false
        )

        assertEquals(systemMessage, chat.prompt)
        assertFalse(chat.isFromUser)
    }

    @Test
    fun chat_withEmptyPrompt_createsValidInstance() {
        val chat = Chat(
            prompt = "",
            bitmap = null,
            isFromUser = true
        )

        assertEquals("", chat.prompt)
    }

    @Test
    fun chat_equalityWorks_forIdenticalChats() {
        val bitmap = mock(Bitmap::class.java)
        val chat1 = Chat("Hello", bitmap, true)
        val chat2 = Chat("Hello", bitmap, true)

        assertEquals(chat1, chat2)
    }

    @Test
    fun chat_notEqual_withDifferentPrompt() {
        val chat1 = Chat("Prompt 1", null, true)
        val chat2 = Chat("Prompt 2", null, true)

        assertNotEquals(chat1, chat2)
    }

    @Test
    fun chat_notEqual_withDifferentIsFromUser() {
        val chat1 = Chat("Prompt", null, true)
        val chat2 = Chat("Prompt", null, false)

        assertNotEquals(chat1, chat2)
    }

    @Test
    fun chat_copy_createsNewInstanceWithSameValues() {
        val original = Chat(
            prompt = "Original prompt",
            bitmap = null,
            isFromUser = true
        )

        val copy = original.copy()

        assertEquals(original, copy)
        assertEquals(original.prompt, copy.prompt)
        assertEquals(original.bitmap, copy.bitmap)
        assertEquals(original.isFromUser, copy.isFromUser)
    }

    @Test
    fun chat_copy_withModifiedPrompt_createsNewInstance() {
        val original = Chat(
            prompt = "Original",
            bitmap = null,
            isFromUser = true
        )

        val modified = original.copy(prompt = "Modified")

        assertEquals("Modified", modified.prompt)
        assertEquals(original.bitmap, modified.bitmap)
        assertEquals(original.isFromUser, modified.isFromUser)
        assertNotEquals(original.prompt, modified.prompt)
    }

    @Test
    fun chat_copy_withModifiedIsFromUser_createsNewInstance() {
        val original = Chat(
            prompt = "Test",
            bitmap = null,
            isFromUser = true
        )

        val modified = original.copy(isFromUser = false)

        assertEquals(original.prompt, modified.prompt)
        assertFalse(modified.isFromUser)
    }

    @Test
    fun chat_hashCodeWorks_forIdenticalChats() {
        val bitmap = mock(Bitmap::class.java)
        val chat1 = Chat("Test", bitmap, true)
        val chat2 = Chat("Test", bitmap, true)

        assertEquals(chat1.hashCode(), chat2.hashCode())
    }

    @Test
    fun chat_withLongPrompt_createsValidInstance() {
        val longPrompt = "A".repeat(1000)
        val chat = Chat(
            prompt = longPrompt,
            bitmap = null,
            isFromUser = false
        )

        assertEquals(longPrompt, chat.prompt)
        assertEquals(1000, chat.prompt.length)
    }

    @Test
    fun chat_withBitmapAndUser_createsValidInstance() {
        val bitmap = mock(Bitmap::class.java)
        val chat = Chat(
            prompt = "Image message",
            bitmap = bitmap,
            isFromUser = true
        )

        assertEquals("Image message", chat.prompt)
        assertEquals(bitmap, chat.bitmap)
        assertTrue(chat.isFromUser)
    }
}
