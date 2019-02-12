package com.zimskasola.myawesometodoapp

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newEntryButton.setOnClickListener {
            showNewEntryDialog()
        }

        refreshButton.setOnClickListener {
        }

        logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            finish()
        }

        updateList()
    }

    private fun showNewEntryDialog() {
        val entryEditText = EditText(this)

        AlertDialog.Builder(this)
            .setTitle("Nov vnos")
            .setPositiveButton("OK") { _: DialogInterface, _: Int ->
                addNewEntry(entryEditText.text.toString())
            }
            .setNegativeButton("Prekliči", null)
            .setView(entryEditText)
            .show()
    }

    private fun addNewEntry(entry: String) {
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            return
        }

        val userId = currentUser.uid
        val collectionPath = "/users/$userId/todos"

        firebaseFirestore.collection(collectionPath).add(TodoItem(entry))
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                } else {
                    Toast.makeText(this@MainActivity, "Napaka: ${result.exception}", Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun updateList() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            return
        }

        val userId = currentUser.uid
        val collectionPath = "/users/$userId/todos"

        firebaseFirestore.collection(collectionPath)
            .addSnapshotListener { data, error ->
                if (data != null) {
                    // Naloži podatke

                    val todoItems = data.documents.map { it.toObject(TodoItem::class.java)?.text }
                    itemsListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, todoItems)
                } else {
                    Toast.makeText(this, "Napaka $error", Toast.LENGTH_LONG).show()
                }
            }
    }
}

class TodoItem {
    val text: String

    constructor(text: String) {
        this.text = text
    }

    constructor() {
        this.text = ""
    }
}