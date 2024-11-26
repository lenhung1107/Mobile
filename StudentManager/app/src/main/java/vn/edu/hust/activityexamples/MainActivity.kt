package vn.edu.hust.activityexamples
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  val items = mutableListOf<String>()
  lateinit var listView: ListView
  lateinit var adapter: ArrayAdapter<String>
  lateinit var launcher: ActivityResultLauncher<Intent>
  var editPosition: Int? = null
  lateinit var textResult: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    textResult = findViewById(R.id.text_result)
    listView = findViewById(R.id.listView)

    adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
    listView.adapter = adapter

    // TODO: Thiet lap context menu cho doi tuong ListView
    registerForContextMenu(listView)

    // TODO: Su dung launcher de mo activity va xu ly ket qua tra ve
    launcher = registerForActivityResult(
      ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
      if (result.resultCode == RESULT_OK) {
        val hoten = result.data?.getStringExtra("hoten")
        val mssv = result.data?.getStringExtra("mssv")

        if (!hoten.isNullOrEmpty() && !mssv.isNullOrEmpty()) {
          val studentInfo = "$hoten - $mssv"

          editPosition?.let { position ->
            // Cập nhật sinh viên hiện có
            items[position] = studentInfo
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Student updated", Toast.LENGTH_SHORT).show()
          } ?: run {
            // Thêm sinh viên mới
            items.add(studentInfo)
            adapter.notifyDataSetChanged()
          }
          editPosition = null
        }
      } else {
        Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
      }
    }
  }

  // TODO: Ham khoi tao cho context menu
  override fun onCreateContextMenu(
    menu: ContextMenu?,
    v: View?,
    menuInfo: ContextMenu.ContextMenuInfo?
  ) {
    menuInflater.inflate(R.menu.context_menu, menu)
    super.onCreateContextMenu(menu, v, menuInfo)
  }

  // TODO: Ham xu ly su kien nhan vao context menu
  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    val position = info.position

    return when (item.itemId) {
      R.id.edit -> {
        editPosition = position
        val studentInfo = items[position].split(" - ")
        if (studentInfo.size == 2) { // Ensure valid data
          val intent = Intent(this, AddStudentActivity::class.java).apply {
            putExtra("hoten", studentInfo[0])
            putExtra("mssv", studentInfo[1])
          }
          launcher.launch(intent)
        } else {
          Toast.makeText(this, "Invalid student data", Toast.LENGTH_SHORT).show()
        }
        true
      }
      R.id.remove -> {
        val removedStudent = items[position]
        items.removeAt(position)
        adapter.notifyDataSetChanged()
        Snackbar.make(listView, "Removed: $removedStudent", Snackbar.LENGTH_LONG)
          .setAction("Undo") {
            items.add(position, removedStudent)
            adapter.notifyDataSetChanged()
          }
          .show()
        true
      }
      else -> super.onContextItemSelected(item)
    }
  }

  // TODO: Ham khoi tao option menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.options_menu, menu)
    return true
  }

  // TODO: Ham xu ly su kien nhan vao option menu
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.add_new -> {
        editPosition = null
        val intent = Intent(this, AddStudentActivity::class.java)
        launcher.launch(intent)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}