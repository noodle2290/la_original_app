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

        val id:Int = intent.getIntExtra(Constants.SELECTED_MEMO_ID,0)
        val memo = memoDao.getMemo(id)

        if(id == 0) {
            binding.editSaveButton.setOnClickListener {
                //idはInt型、ここではchipグループで選択されているchipのidを取得して、それをviewに変換している
                val chip:Chip = findViewById(binding.chips.checkedChipId)
                val newMemo = Memo(id, binding.dateEditText.text.toString(),chip.text.toString(), binding.contentEditText.text.toString())
                memoDao.insert(newMemo)
                intentMethod(MainActivity())
            }
        }else{
            binding.dateEditText.setText(memo.date)
            binding.contentEditText.setText(memo.content)
            binding.editSaveButton.setOnClickListener {
                val chip:Chip = findViewById(binding.chips.checkedChipId)
                val afterMemo = Memo(id, binding.dateEditText.text.toString(),chip.text.toString(), binding.contentEditText.text.toString())
                memoDao.update(afterMemo)
                intentMethod(MainActivity(),afterMemo.id)
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
}