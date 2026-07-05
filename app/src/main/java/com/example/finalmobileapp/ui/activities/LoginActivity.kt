package com.example.finalmobileapp.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.finalmobileapp.MainActivity
import com.example.finalmobileapp.data.AppDatabase
import com.example.finalmobileapp.databinding.ActivityLoginBinding
import com.example.finalmobileapp.util.ValidationUtil
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Check if user is already logged in
        val sharedPref = getSharedPreferences("eco_stay_prefs", Context.MODE_PRIVATE)
        if (sharedPref.getLong("user_id", -1L) != -1L) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.tvRegisterLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (!ValidationUtil.isValidEmail(email)) {
            binding.tilEmail.error = "Invalid email"
            return
        } else {
            binding.tilEmail.error = null
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = "Password is required"
            return
        } else {
            binding.tilPassword.error = null
        }

        val db = AppDatabase.getDatabase(this)
        lifecycleScope.launch {
            val user = db.appDao().getUserByEmail(email)
            if (user != null && user.password == password) {
                // Save session
                val sharedPref = getSharedPreferences("eco_stay_prefs", Context.MODE_PRIVATE)
                sharedPref.edit().putLong("user_id", user.id).apply()
                
                Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
