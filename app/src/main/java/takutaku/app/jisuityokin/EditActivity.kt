package takutaku.app.jisuityokin

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.chip.Chip
import takutaku.app.jisuityokin.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    private val memoDao = MemoApplication.db.memoDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        memoDao.deleteAll()
        val id:Int = intent.getIntExtra(Constants.SELECTED_MEMO_ID,0)
        val memo = memoDao.getMemo(id)

        if(id == 0) {
            binding.editSaveButton.setOnClickListener {
                val chip:Chip = findViewById(binding.chips.checkedChipId!!)
                val memo = Memo(id, binding.dateEditText.text.toString(),chip.text.toString(), binding.contentEditText.text.toString())
                memoDao.insert(memo)
                Intent(MainActivity())
            }
        }else{
            binding.dateEditText.setText(memo.date)
            binding.contentEditText.setText(memo.content)
            binding.editSaveButton.setOnClickListener {
                val memo = Memo(id, binding.dateEditText.text.toString(),binding.chips.checkedChipId.toString(), binding.contentEditText.text.toString())
                memoDao.update(memo)
                Intent(MainActivity(),memo.id)
            }
        }

    }
    fun Intent(activity: Activity, vararg ids:Int){
        val activityIntent = android.content.Intent(applicationContext, activity::class.java)
        for(i in ids) {
            activityIntent.putExtra(Constants.SELECTED_MEMO_ID, i)
        }
        startActivity(activityIntent)
    }
}