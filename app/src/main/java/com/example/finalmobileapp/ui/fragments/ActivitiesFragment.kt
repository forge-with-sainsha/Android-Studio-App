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
import com.example.finalmobileapp.data.entity.ActivityEntity
import com.example.finalmobileapp.databinding.DialogBookingBinding
import com.example.finalmobileapp.databinding.FragmentActivitiesBinding
import com.example.finalmobileapp.ui.adapters.ActivityAdapter
import com.example.finalmobileapp.util.NotificationHelper
import kotlinx.coroutines.launch
import java.util.*

class ActivitiesFragment : Fragment() {
    private var _binding: FragmentActivitiesBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: DataRepository
    private lateinit var adapter: ActivityAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentActivitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = DataRepository(requireContext())
        
        adapter = ActivityAdapter(emptyList()) { activity ->
            showBookingDialog(activity)
        }
        binding.rvActivities.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            val activities = repository.getActivities()
            adapter.updateData(activities)
        }
    }

    private fun showBookingDialog(activity: ActivityEntity) {
        val dialogBinding = DialogBookingBinding.inflate(LayoutInflater.from(requireContext()))
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.tvDialogTitle.text = "Reserve ${activity.name}"
        dialogBinding.tilQuantity.hint = "Number of Participants"
        
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
                repository.book(userId, "ACTIVITY", activity.id, activity.name, selectedDate, quantity, activity.price)
                NotificationHelper.showBookingNotification(requireContext(), "Activity Reserved", "You are booked for ${activity.name}!")
                Toast.makeText(requireContext(), "Reservation successful!", Toast.LENGTH_SHORT).show()
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
