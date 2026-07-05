package com.example.finalmobileapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalmobileapp.data.entity.RoomEntity
import com.example.finalmobileapp.databinding.ItemRoomBinding

class RoomAdapter(
    private var rooms: List<RoomEntity>,
    private val onBookClick: (RoomEntity) -> Unit
) : RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    class RoomViewHolder(val binding: ItemRoomBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = rooms[position]
        holder.binding.apply {
            tvRoomName.text = room.name
            tvRoomDescription.text = room.description
            tvRoomPrice.text = "$${room.price} / night"
            ivRoom.setImageResource(room.imageResId)
            btnBookRoom.setOnClickListener { onBookClick(room) }
        }
    }

    override fun getItemCount() = rooms.size

    fun updateData(newRooms: List<RoomEntity>) {
        rooms = newRooms
        notifyDataSetChanged()
    }
}
