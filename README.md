**EcoStay Retreat** 

---

## ✨ Features

### 👤 1. Authentication & Profile Management
* **Secure Registration & Login:** Form validation with real-time feedback (verifying emails, secure passwords, and matching fields).
* **Profile Management:** View logged-in user profile, credentials, and track total bookings.

### 🏨 2. Eco-Friendly Room Bookings
* **Sustainable Accommodations:** Browse unique, curated lodgings such as *Eco Pods*, *Forest Huts*, and *Mountain View Cabins*.
* **Interactive Booking:** Check-in and check-out date selections, cost calculators, and real-time confirmations.

### 🚴 3. Low-Impact Eco-Activities
* **Sustainable Workshops:** Register for sustainable activities like *Bird Watching*, *Eco-Tours*, *Organic Cooking Workshops*, and *Forest Hiking*.
* **Integrated Booking:** Join/leave workshops and manage activity timetables directly within the app.

### 📚 4. Eco-Education
* **Eco-Tips Tab:** Provides users with valuable insights and tips on how to reduce their carbon footprint during travel and lodging.

### 💾 5. Offline-First Architecture (Room Database)
* **Local Persistence:** Powered by SQLite through **Jetpack Room Database**. Bookings, registered activities, rooms, and sessions are saved locally so the app remains functional without internet access.

---

## 🛠️ Tech Stack & Architecture

This project follows Android's recommended guidelines for a clean, modular, and maintainable codebase:

* **Language:** [Kotlin](https://kotlinlang.org) (100% Type-safe & Expressive)
* **UI Pattern:** Single Activity Architecture with Fragment-based Navigation (`Jetpack Navigation Component`)
* **Local Storage:** [Jetpack Room Database](https://developer.android.com/training/data-storage/room) (Data Access Objects (DAOs), Entity Relations, and Migrations)
* **Data Flow:** [Repository Pattern](https://developer.android.com/topic/libraries/architecture/data-layer) acting as a single source of truth for UI data
* **Binding:** `View Binding` to safely interact with XML layout elements (eliminating `findViewById` boilerplate)
* **Design:** [Google Material Design Components](https://material.io/design) for consistent spacing, custom typography, and modern inputs.

 to build and install the app.

---
