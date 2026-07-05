package com.example.finalmobileapp.data.dao

import androidx.room.*
import com.example.finalmobileapp.data.entity.*

@Dao
interface AppDao {
    // User
    @Insert
    suspend fun registerUser(user: User): Long

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUserById(userId: Long): User?

    // Rooms
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRooms(rooms: List<RoomEntity>)

    @Query("SELECT * FROM rooms")
    suspend fun getAllRooms(): List<RoomEntity>

    // Activities
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivities(activities: List<ActivityEntity>)

    @Query("SELECT * FROM activities")
    suspend fun getAllActivities(): List<ActivityEntity>

    // Bookings
    @Insert
    suspend fun insertBooking(booking: Booking)

    @Query("SELECT * FROM bookings WHERE userId = :userId ORDER BY id DESC")
    suspend fun getBookingsForUser(userId: Long): List<Booking>
}
