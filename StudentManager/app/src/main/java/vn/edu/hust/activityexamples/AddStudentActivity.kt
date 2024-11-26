package vn.edu.hust.activityexamples

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
  private lateinit var editHoten: EditText
  private lateinit var editMssv: EditText

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_student)

    editHoten = findViewById(R.id.edit_hoten)
    editMssv = findViewById(R.id.edit_mssv)

    // Receive and display initial data
    val hoten = intent.getStringExtra("hoten")
    val mssv = intent.getStringExtra("mssv")

    editHoten.setText(hoten) // Set name
    editMssv.setText(mssv) // Set ID

    // Save button
    findViewById<Button>(R.id.button_ok).setOnClickListener {
      val newHoten = editHoten.text.toString().trim()
      val newMssv = editMssv.text.toString().trim()

      if (newHoten.isNotEmpty() && newMssv.isNotEmpty()) {
        // Return updated data to MainActivity
        val resultIntent = Intent().apply {
          putExtra("hoten", newHoten)
          putExtra("mssv", newMssv)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
      } else {
        Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
      }
    }

    // Cancel button
    findViewById<Button>(R.id.button_cancel).setOnClickListener {
      setResult(Activity.RESULT_CANCELED)
      finish()
    }
  }
}
