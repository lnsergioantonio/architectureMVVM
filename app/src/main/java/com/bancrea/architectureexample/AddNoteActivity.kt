package com.bancrea.architectureexample

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.NumberPicker
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class AddNoteActivity : AppCompatActivity() {
    companion object{
        val EXTRA_TITLE="TITLE"
        val EXTRA_DESCRIPTION="DESCRIPTION"
        val EXTRA_NUMBER="NUMBER"
    }

    private lateinit var title: TextInputEditText
    private lateinit var description:TextInputEditText
    private lateinit var numberPicker: NumberPicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        setTitle("Add note")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.save_note){
            saveNote()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {
        val mTitle= title.text.toString()
        val mDescription = description.text.toString()
        val mNumber = numberPicker.value

        if(mTitle.trim().isEmpty() || mDescription.trim().isEmpty()){
            Toast.makeText(this,"Please insert title and description",Toast.LENGTH_LONG).show()
            return
        }

        val intent = Intent()
        intent.putExtra(EXTRA_TITLE,mTitle)
        intent.putExtra(EXTRA_DESCRIPTION,mDescription)
        intent.putExtra(EXTRA_NUMBER,mNumber)
        setResult(RESULT_OK,intent)
        finish()
    }
}
