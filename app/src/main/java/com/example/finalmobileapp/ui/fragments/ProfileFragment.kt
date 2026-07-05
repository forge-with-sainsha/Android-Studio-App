package com.example.finalmobileapp.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.finalmobileapp.data.AppDatabase
import com.example.finalmobileapp.databinding.FragmentProfileBinding
import com.example.finalmobileapp.ui.activities.LoginActivity
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("eco_stay_prefs", Context.MODE_PRIVATE)
        val userId = sharedPref.getLong("user_id", -1L)

        if (userId != -1L) {
            val db = AppDatabase.getDatabase(requireContext())
            viewLifecycleOwner.lifecycleScope.launch {
                val user = db.appDao().getUserById(userId)
                user?.let {
                    binding.tvProfileName.text = it.name
                    binding.tvProfileEmail.text = it.email
                }
            }
        }

        binding.btnLogout.setOnClickListener {
            sharedPref.edit().clear().apply()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
