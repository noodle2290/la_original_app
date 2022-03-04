package takutaku.app.jisuityokin

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.android.material.chip.Chip
import takutaku.app.jisuityokin.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    private val memoDao = MemoApplication.db.memoDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        val id:Int = intent.getIntExtra(Constants.SELECTED_MEMO_ID,0)
        val memo = memoDao.getMemo(id)

        when(id){
            0 -> {
                binding.editDeleteButton.isInvisible = true
                binding.editSaveButton.setOnClickListener {
                    memoDao.insert(postData(id))
                    intentMethod(MainActivity())
                }
            }
            else->{
                binding.editDeleteButton.isVisible = true
                binding.dateEditText.setText(memo.date)
                binding.contentEditText.setText(memo.content)
                binding.editSaveButton.setOnClickListener {
                    memoDao.update(postData(id))
                    intentMethod(MainActivity(),postData(id).id)
                }
                binding.editDeleteButton.setOnClickListener {
                    memoDao.delete(memo)
                    intentMethod(MainActivity())
                }
            }
        }
    }
    private fun intentMethod(activity: Activity, vararg ids:Int){
        val activityIntent = android.content.Intent(applicationContext, activity::class.java)
        for(i in ids) {
            activityIntent.putExtra(Constants.SELECTED_MEMO_ID, i)
        }
        startActivity(activityIntent)
    }

    private fun postData(id:Int):Memo{
        val chip:Chip = findViewById(binding.chips.checkedChipId)
        return Memo(id, binding.dateEditText.text.toString(),chip.text.toString(), binding.contentEditText.text.toString())
    }
}