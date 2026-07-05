package com.example.finalmobileapp.data

import android.content.Context
import com.example.finalmobileapp.R
import com.example.finalmobileapp.data.entity.*

class DataRepository(private val context: Context) {
    private val appDao = AppDatabase.getDatabase(context).appDao()

    suspend fun getRooms(): List<RoomEntity> {
        val rooms = appDao.getAllRooms()
        if (rooms.isEmpty()) {
            val initialRooms = listOf(
                RoomEntity(name = "EcoPod", description = "Experience a unique stay surrounded by the beauty of nature, where comfort and sustainability come together seamlessly. Designed with environmentally friendly materials and energy-efficient features, this accommodation offers a peaceful escape from everyday life. Wake up to fresh mountain air, stunning views, and a calm atmosphere that encourages relaxation and rejuvenation. Perfect for travelers seeking a memorable getaway while staying connected to the natural environment", price = 45.0, imageResId = R.drawable.ecopods),
                RoomEntity(name = "Mountain View Cabin", description = "Perched among breathtaking highland scenery, this cozy retreat offers guests a peaceful escape surrounded by nature. Large panoramic windows provide stunning views of rolling hills and lush forests throughout the day. Built with sustainable materials and eco-friendly features, it combines comfort with environmental responsibility. Guests can relax in a warm, tranquil atmosphere while enjoying fresh mountain air and unforgettable sunrises.", price = 120.0, imageResId = R.drawable.mountain_view_cabin),
                RoomEntity(name = "Forest Hut", description = "Surrounded by towering trees and the calming sounds of nature, this cozy retreat offers a peaceful escape from everyday life. Built with sustainable materials and designed to blend seamlessly with its natural surroundings, it provides both comfort and environmental responsibility. Guests can enjoy fresh mountain air, scenic woodland views, and a warm rustic atmosphere. It is the perfect choice for travelers seeking relaxation, privacy, and a deeper connection with nature", price = 75.0, imageResId = R.drawable.forest_hut),
                RoomEntity(name = "Luxury Eco Suite", description = "Experience the perfect blend of comfort, elegance, and sustainability in a beautifully designed retreat surrounded by nature. Featuring spacious interiors, eco-friendly furnishings, and stunning panoramic views, this accommodation offers a peaceful escape from everyday life. Guests can enjoy modern amenities while minimizing their environmental footprint through energy-efficient and sustainable features. Ideal for relaxation and rejuvenation, it provides a memorable stay in harmony with the natural environment.", price = 250.0, imageResId =R.drawable.eco_suite)
            )
            appDao.insertRooms(initialRooms)
            return initialRooms
        }
        return rooms
    }

    suspend fun getActivities(): List<ActivityEntity> {
        val activities = appDao.getAllActivities()
        if (activities.isEmpty()) {
            val initialActivities = listOf(
                ActivityEntity(name = "Guided Hike", description = "Discover stunning mountain trails, breathtaking views, and the beauty of nature with the guidance of experienced local experts. Enjoy a memorable outdoor experience, learn about the surrounding landscape, and take in the fresh mountain atmosphere while creating lasting memories along the way.", price = 25.0, imageResId = R.drawable.hike),
                ActivityEntity(name = "Eco Tour", description = "Discover the rich natural beauty, wildlife, and cultural heritage of the region through a thoughtfully guided eco tour. Learn about local ecosystems, conservation efforts, and sustainable living practices while enjoying meaningful experiences that connect you with nature and the surrounding environment.", price = 15.0, imageResId = R.drawable.eco_tour),
                ActivityEntity(name = "Bird Watching", description = "Discover the fascinating world of birdlife through a guided bird-watching experience. Spot unique species, learn about their habitats and behaviors, and enjoy a relaxing outdoor activity surrounded by the beauty and tranquility of nature.", price = 10.0, imageResId =R.drawable.bird_watching),
                ActivityEntity(name = "Sustainability Workshop", description = "Learn practical, hands-on eco-friendly skills designed for tourists, focusing on sustainable living and responsible travel. Take part in simple, engaging activities that help you connect with nature, reduce environmental impact, and understand local conservation practices while enjoying a meaningful and interactive experience.", price = 30.0, imageResId =R.drawable.cooking)
            )
            appDao.insertActivities(initialActivities)
            return initialActivities
        }
        return activities
    }

    suspend fun book(userId: Long, type: String, itemId: Int, itemName: String, date: String, quantity: Int, price: Double) {
        val totalPrice = price * quantity
        appDao.insertBooking(Booking(
            userId = userId,
            type = type,
            itemId = itemId,
            itemName = itemName,
            bookingDate = date,
            quantity = quantity,
            totalPrice = totalPrice
        ))
    }

    suspend fun getBookings(userId: Long): List<Booking> {
        return appDao.getBookingsForUser(userId)
    }
}
