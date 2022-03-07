package takutaku.app.jisuityokin

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
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

    private lateinit var dataStore: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        dataStore = this.getSharedPreferences("DataStore", Context.MODE_PRIVATE)

        val id:Int = intent.getIntExtra(Constants.SELECTED_MEMO_ID,0)
        val memo = memoDao.getMemo(id)
        val count = dataStore.getInt(Constants.COUNT_NUMBER,0)

        when(id){
            0 -> {
                //新規投稿処理
                binding.editDeleteButton.isInvisible = true
                binding.editSaveButton.setOnClickListener {
                    plusCount(count)
                    memoDao.insert(postData(id))
                    intentMethod(MainActivity())
                }
            }
            else->{
                //編集処理
                binding.editDeleteButton.isVisible = true
                binding.dateEditText.setText(memo.date)
                binding.contentEditText.setText(memo.content)

                binding.editSaveButton.setOnClickListener {
                    when(memo.check){
                        "自炊成功" ->{
                            minusCount(count)
                        }
                        "自炊失敗" ->{
                            plusCount(count)
                        }
                    }
                    memoDao.update(postData(id))
                    intentMethod(MainActivity(),postData(id).id)
                }

                binding.editDeleteButton.setOnClickListener {
                    if(memo.check == "自炊成功") {
                        val editor = dataStore.edit()
                        editor.putInt(Constants.COUNT_NUMBER, count - 1)
                        editor.apply()
                    }
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

    private fun plusCount(count:Int){
        val chip:Chip = findViewById(binding.chips.checkedChipId)
        if (chip == binding.chip1){
            val editor = dataStore.edit()
            editor.putInt(Constants.COUNT_NUMBER, count + 1)
            editor.apply()
        }
    }

    private fun minusCount(count:Int){
        val chip:Chip = findViewById(binding.chips.checkedChipId)
        if(chip == binding.chip2) {
            val editor = dataStore.edit()
            editor.putInt(Constants.COUNT_NUMBER, count - 1)
            editor.apply()
        }

    }
}