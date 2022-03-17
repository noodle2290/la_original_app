package takutaku.app.jisuityokin

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import takutaku.app.jisuityokin.databinding.ActivityEditBinding
import takutaku.app.jisuityokin.fragment.CalendarFragment
import takutaku.app.jisuityokin.fragment.CalendarViewModel

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    private val memoDao = MemoApplication.db.memoDao()

    private lateinit var dataStore: SharedPreferences

    lateinit var viewModel: CalendarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        dataStore = this.getSharedPreferences("DataStore", Context.MODE_PRIVATE)

        viewModel = ViewModelProvider(this)[CalendarViewModel::class.java]

        val id:Int = intent.getIntExtra(Constants.SELECTED_MEMO_ID,0)
        val memo = memoDao.getMemo(id)
        val count = dataStore.getInt(Constants.COUNT_NUMBER,0)

        //カレンダーフラグメントを開く
        binding.dateButton.setOnClickListener{
            val newFragment = CalendarFragment()
            newFragment.show(supportFragmentManager, "datePicker")
        }

        when(id){
            0 -> {
                //新規投稿処理
                binding.editDeleteButton.isInvisible = true
                dateSet()
                binding.editSaveButton.setOnClickListener {
                    plusCount(count)
                    memoDao.insert(postData(id))
                    intentMethod(MainActivity())
                }
            }
            else->{
                //編集処理
                binding.editDeleteButton.isVisible = true
                changeDate(memo)
                dateSet()
                binding.contentEditText.setText(memo.content)

                binding.editSaveButton.setOnClickListener {
                    memoDao.update(postData(id))
                    intentMethod(MainActivity())
                }

                binding.editDeleteButton.setOnClickListener {
                    minusCount(count)
                    memoDao.delete(memo)
                    intentMethod(MainActivity())
                }
            }
        }
    }

    private fun intentMethod(activity: Activity){
        val activityIntent = android.content.Intent(applicationContext, activity::class.java)
        startActivity(activityIntent)
    }

    //DateButtonの文字を変える
    private fun dateSet(){
        val year = viewModel.year
        val month = viewModel.month + 1
        val day = viewModel.day
        binding.dateButton.text = "${year}年${month}月${day}日"
    }

    //ViewModelに日付を代入
    private fun changeDate(memo:Memo){
        viewModel.year = memo.year!!
        viewModel.month = memo.month!!
        viewModel.day = memo.day!!
    }

    private fun postData(id:Int):Memo{
        return Memo(id, viewModel.year,viewModel.month, viewModel.day,findViewById<Chip>(binding.chips.checkedChipId).text.toString(),binding.contentEditText.text.toString())
    }

    private fun plusCount(count:Int){
        val editor = dataStore.edit()
        editor.putInt(Constants.COUNT_NUMBER, count + 1)
        editor.apply()
    }

    private fun minusCount(count:Int){
        val editor = dataStore.edit()
        editor.putInt(Constants.COUNT_NUMBER, count - 1)
        editor.apply()
    }
}