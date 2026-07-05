package com.example.finalmobileapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class Booking(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val type: String, // "ROOM" or "ACTIVITY"
    val itemId: Int,
    val itemName: String,
    val bookingDate: String,
    val quantity: Int, // nights or participants
    val totalPrice: Double
)
