package com.example.finalmobileapp.ui.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.finalmobileapp.data.DataRepository
import com.example.finalmobileapp.data.entity.RoomEntity
import com.example.finalmobileapp.databinding.DialogBookingBinding
import com.example.finalmobileapp.databinding.FragmentRoomsBinding
import com.example.finalmobileapp.ui.adapters.RoomAdapter
import com.example.finalmobileapp.util.NotificationHelper
import kotlinx.coroutines.launch
import java.util.*

class RoomsFragment : Fragment() {
    private var _binding: FragmentRoomsBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: DataRepository
    private lateinit var adapter: RoomAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRoomsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = DataRepository(requireContext())
        
        adapter = RoomAdapter(emptyList()) { room ->
            showBookingDialog(room)
        }
        binding.rvRooms.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            val rooms = repository.getRooms()
            adapter.updateData(rooms)
        }
    }

    private fun showBookingDialog(room: RoomEntity) {
        val dialogBinding = DialogBookingBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.tvDialogTitle.text = "Book ${room.name}"
        dialogBinding.tilQuantity.hint = "Number of Nights"
        
        var selectedDate = ""
        dialogBinding.btnSelectDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, year, month, day ->
                selectedDate = "$day/${month + 1}/$year"
                dialogBinding.btnSelectDate.text = selectedDate
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        dialogBinding.btnConfirmBooking.setOnClickListener {
            val quantityStr = dialogBinding.etQuantity.text.toString()
            if (selectedDate.isEmpty()) {
                Toast.makeText(requireContext(), "Please select a date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val quantity = quantityStr.toIntOrNull()
            if (quantity == null || quantity <= 0) {
                dialogBinding.tilQuantity.error = "Invalid quantity"
                return@setOnClickListener
            }

            val sharedPref = requireActivity().getSharedPreferences("eco_stay_prefs", Context.MODE_PRIVATE)
            val userId = sharedPref.getLong("user_id", -1L)

            viewLifecycleOwner.lifecycleScope.launch {
                repository.book(userId, "ROOM", room.id, room.name, selectedDate, quantity, room.price)
                NotificationHelper.showBookingNotification(requireContext(), "Booking Confirmed", "Your stay at ${room.name} is reserved!")
                Toast.makeText(requireContext(), "Booking successful!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
