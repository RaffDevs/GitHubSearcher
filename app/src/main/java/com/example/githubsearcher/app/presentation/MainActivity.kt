package com.example.githubsearcher.app.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearcher.app.presentation.adapter.RepositoryAdapter
import com.example.githubsearcher.databinding.ActivityMainBinding
import com.example.githubsearcher.infra.cache.UserPreferencesImpl

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchButton: Button
    private lateinit var textUsername: TextView
    private lateinit var editSearchField: AutoCompleteTextView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: RepositoryAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var userPreferences: UserPreferencesImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progressBarLoad
        textUsername = binding.textUsername
        editSearchField = binding.editUsername
        searchButton = binding.buttonSearch
        recyclerView = binding.recyclerViewRepository
        adapter = RepositoryAdapter(this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        userPreferences = UserPreferencesImpl(this)

        setupListeners()

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            hideKeyboard()
        }
        return super.onTouchEvent(event)
    }

    private fun setupListeners() {
        searchButton.setOnClickListener {
            searchButtonAction()
        }

        editSearchField.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard()
                return@setOnKeyListener true
            }
            false
        }


        viewModel.repositories.observe(this) { listRepository ->
            adapter.updateRepositories(listRepository)
        }

        viewModel.requestState.observe(this) { state ->
            progressBar.visibility = View.GONE
            if (state is RequestState.Error) {
                state.error?.let {
                    val description = "Parece que o servidor não respondeu como esperado... " +
                            "Por favor, tente outro usuário ou tente novamente mais tarde!"
                    showErrorDialog(it, description)
                }
            } else if (state is RequestState.Failure) {
                state.throwable.message?.let {
                    val description = "Um erro inesperado ocorreu!"
                    showErrorDialog(it, description)
                }
            } else {
                recyclerView.visibility = View.VISIBLE
            }
        }

        viewModel.username.observe(this) { username ->
            textUsername.isVisible = true
            textUsername.text = username
        }
    }

    private fun saveSuggestions(user: String) {
        userPreferences.addUser(user)
        val suggestions = userPreferences.getUser()

        suggestions?.let {
            val adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, it)
            editSearchField.setAdapter(adapter)
        }

    }

    private fun showErrorDialog(message: String, description: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desculpe por isso...")
        builder.setMessage(description)
        builder.setPositiveButton("OK") { dialog, _ ->
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

    }

    private fun searchButtonAction() {
        val userName = editSearchField.text.toString()
        viewModel.getRepositoriesByUser(userName)
        editSearchField.text.clear()
        saveSuggestions(userName)
        hideKeyboard()
        progressBar.visibility = View.VISIBLE
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editSearchField.windowToken, 0)
    }

}