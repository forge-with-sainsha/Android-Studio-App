package com.example.finalmobileapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalmobileapp.data.entity.Booking
import com.example.finalmobileapp.databinding.ItemBookingBinding

class BookingAdapter(
    private var bookings: List<Booking>
) : RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    class BookingViewHolder(val binding: ItemBookingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding = ItemBookingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookings[position]
        holder.binding.apply {
            tvBookingType.text = booking.type
            tvBookingName.text = booking.itemName
            tvBookingDate.text = "Date: ${booking.bookingDate}"
            val detailLabel = if (booking.type == "ROOM") "Nights: " else "Participants: "
            tvBookingDetails.text = "$detailLabel${booking.quantity}"
            tvBookingTotal.text = "Total: $${String.format("%.2f", booking.totalPrice)}"
        }
    }

    override fun getItemCount() = bookings.size

    fun updateData(newBookings: List<Booking>) {
        bookings = newBookings
        notifyDataSetChanged()
    }
}
