package com.example.finalmobileapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.finalmobileapp.data.DataRepository
import com.example.finalmobileapp.databinding.FragmentBookingsBinding
import com.example.finalmobileapp.ui.adapters.BookingAdapter
import kotlinx.coroutines.launch

class BookingsFragment : Fragment() {
    private var _binding: FragmentBookingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: DataRepository
    private lateinit var adapter: BookingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBookingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = DataRepository(requireContext())
        adapter = BookingAdapter(emptyList())
        binding.rvBookings.adapter = adapter

        val sharedPref = requireActivity().getSharedPreferences("eco_stay_prefs", Context.MODE_PRIVATE)
        val userId = sharedPref.getLong("user_id", -1L)

        if (userId != -1L) {
            viewLifecycleOwner.lifecycleScope.launch {
                val bookings = repository.getBookings(userId)
                adapter.updateData(bookings)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
