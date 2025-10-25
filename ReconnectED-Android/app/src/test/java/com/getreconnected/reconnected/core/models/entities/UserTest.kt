package com.getreconnected.reconnected.core.models.entities

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Unit tests for User data class
 */
class UserTest {

    @Test
    fun user_createsInstanceWithAllFields() {
        val uid = "user123"
        val firstName = "John"
        val lastName = "Doe"
        val email = "john.doe@example.com"
        val created = 1609459200
        val lastSignIn = 1640995200

        val user = User(
            uid = uid,
            firstName = firstName,
            lastName = lastName,
            email = email,
            created = created,
            lastSignIn = lastSignIn
        )

        assertEquals(uid, user.uid)
        assertEquals(firstName, user.firstName)
        assertEquals(lastName, user.lastName)
        assertEquals(email, user.email)
        assertEquals(created, user.created)
        assertEquals(lastSignIn, user.lastSignIn)
    }

    @Test
    fun user_createsInstanceWithOptionalFieldsNull() {
        val uid = "user456"
        val email = "user@example.com"
        val created = 1609459200
        val lastSignIn = 1640995200

        val user = User(
            uid = uid,
            firstName = null,
            lastName = null,
            email = email,
            created = created,
            lastSignIn = lastSignIn
        )

        assertEquals(uid, user.uid)
        assertNull(user.firstName)
        assertNull(user.lastName)
        assertEquals(email, user.email)
    }

    @Test
    fun user_createsInstanceWithDefaultValues() {
        val uid = "user789"
        val email = "default@example.com"
        val created = 1609459200
        val lastSignIn = 1640995200

        val user = User(
            uid = uid,
            email = email,
            created = created,
            lastSignIn = lastSignIn
        )

        assertNull(user.firstName)
        assertNull(user.lastName)
    }

    @Test
    fun user_equalityWorks_forIdenticalUsers() {
        val user1 = User(
            uid = "user1",
            firstName = "Alice",
            lastName = "Smith",
            email = "alice@example.com",
            created = 1609459200,
            lastSignIn = 1640995200
        )
        val user2 = User(
            uid = "user1",
            firstName = "Alice",
            lastName = "Smith",
            email = "alice@example.com",
            created = 1609459200,
            lastSignIn = 1640995200
        )

        assertEquals(user1, user2)
    }

    @Test
    fun user_notEqual_withDifferentUid() {
        val user1 = User(
            uid = "user1",
            email = "user@example.com",
            created = 1609459200,
            lastSignIn = 1640995200
        )
        val user2 = User(
            uid = "user2",
            email = "user@example.com",
            created = 1609459200,
            lastSignIn = 1640995200
        )

        assertNotEquals(user1, user2)
    }

    @Test
    fun user_copy_createsNewInstanceWithSameValues() {
        val original = User(
            uid = "original",
            firstName = "Test",
            lastName = "User",
            email = "test@example.com",
            created = 1609459200,
            lastSignIn = 1640995200
        )

        val copy = original.copy()

        assertEquals(original, copy)
        assertEquals(original.uid, copy.uid)
        assertEquals(original.firstName, copy.firstName)
        assertEquals(original.lastName, copy.lastName)
    }

    @Test
    fun user_copy_withModifiedFields_createsNewInstance() {
        val original = User(
            uid = "user1",
            firstName = "John",
            lastName = "Doe",
            email = "john@example.com",
            created = 1609459200,
            lastSignIn = 1640995200
        )

        val modified = original.copy(
            firstName = "Jane",
            lastSignIn = 1672531200
        )

        assertEquals("user1", modified.uid)
        assertEquals("Jane", modified.firstName)
        assertEquals("Doe", modified.lastName)
        assertEquals("john@example.com", modified.email)
        assertEquals(1609459200, modified.created)
        assertEquals(1672531200, modified.lastSignIn)
    }

    @Test
    fun user_hashCodeWorks_forIdenticalUsers() {
        val user1 = User(
            uid = "user1",
            firstName = "Bob",
            email = "bob@example.com",
            created = 1609459200,
            lastSignIn = 1640995200
        )
        val user2 = User(
            uid = "user1",
            firstName = "Bob",
            email = "bob@example.com",
            created = 1609459200,
            lastSignIn = 1640995200
        )

        assertEquals(user1.hashCode(), user2.hashCode())
    }

    @Test
    fun user_withEmptyEmail_createsValidInstance() {
        val user = User(
            uid = "user123",
            email = "",
            created = 1609459200,
            lastSignIn = 1640995200
        )

        assertEquals("", user.email)
    }

    @Test
    fun user_withZeroTimestamps_createsValidInstance() {
        val user = User(
            uid = "user123",
            email = "user@example.com",
            created = 0,
            lastSignIn = 0
        )

        assertEquals(0, user.created)
        assertEquals(0, user.lastSignIn)
    }
}
