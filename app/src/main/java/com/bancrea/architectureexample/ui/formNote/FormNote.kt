package com.bancrea.architectureexample.ui.formNote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.NumberPicker
import android.widget.Toast
import com.bancrea.architectureexample.R
import com.google.android.material.textfield.TextInputEditText

class FormNote : AppCompatActivity() {
    companion object{
        val EXTRA_ID = "ID"
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

        title = findViewById(R.id.act_add_note_input_title)
        description = findViewById(R.id.act_add_note_input_description)
        numberPicker = findViewById(R.id.act_add_note_number_picker)
        numberPicker.minValue=0
        numberPicker.maxValue=10
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        setTitle("Add note")

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit note")
            title.setText(intent.getStringExtra(EXTRA_TITLE))
            description.setText(intent.getStringExtra(EXTRA_DESCRIPTION))
            numberPicker.value= intent.getIntExtra(EXTRA_NUMBER,0)
        }

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

        val data = Intent()
        if(intent.hasExtra(EXTRA_ID))
            data.putExtra(
                EXTRA_ID,intent.getIntExtra(
                    EXTRA_ID,-1))

        data.putExtra(EXTRA_TITLE,mTitle)
        data.putExtra(EXTRA_DESCRIPTION,mDescription)
        data.putExtra(EXTRA_NUMBER,mNumber)
        setResult(RESULT_OK,data)
        finish()
    }
}
