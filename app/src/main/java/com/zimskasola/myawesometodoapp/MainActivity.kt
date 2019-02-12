package com.zimskasola.myawesometodoapp

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val todoItems = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newEntryButton.setOnClickListener {
            showNewEntryDialog()
        }

        refreshButton.setOnClickListener {
            //TODO
        }
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
        todoItems.add(entry)
        updateList()
    }

    private fun updateList() {
        // Ne uporabljat ListView tako kot tukaj, da se vsakič nastavi nov adapter
        // Tukaj je narejeno tako samo zato, da je koda manj zapletena
        itemsListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, todoItems)
    }
}
