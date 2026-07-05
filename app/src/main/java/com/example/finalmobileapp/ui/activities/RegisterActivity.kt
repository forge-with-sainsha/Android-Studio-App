package com.example.finalmobileapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.finalmobileapp.data.AppDatabase
import com.example.finalmobileapp.data.entity.User
import com.example.finalmobileapp.databinding.ActivityRegisterBinding
import com.example.finalmobileapp.util.ValidationUtil
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            register()
        }

        binding.tvLoginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun register() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (name.isEmpty()) {
            binding.tilName.error = "Name is required"
            return
        } else {
            binding.tilName.error = null
        }

        if (!ValidationUtil.isValidEmail(email)) {
            binding.tilEmail.error = "Invalid email"
            return
        } else {
            binding.tilEmail.error = null
        }

        if (!ValidationUtil.isValidPassword(password)) {
            binding.tilPassword.error = "Password must be at least 6 characters"
            return
        } else {
            binding.tilPassword.error = null
        }

        val db = AppDatabase.getDatabase(this)
        lifecycleScope.launch {
            val existingUser = db.appDao().getUserByEmail(email)
            if (existingUser != null) {
                Toast.makeText(this@RegisterActivity, "Email already registered", Toast.LENGTH_SHORT).show()
            } else {
                db.appDao().registerUser(User(name = name, email = email, password = password))
                Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}
