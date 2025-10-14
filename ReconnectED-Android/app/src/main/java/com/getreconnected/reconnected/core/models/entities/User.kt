package com.getreconnected.reconnected.core.models.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: String,
    val firstName: String?,
    val lastName: String?,
    val email: String,
    val created: Int,
    val lastSignIn: Int,
    @Ignore val avatar: Bitmap? = null,
)
