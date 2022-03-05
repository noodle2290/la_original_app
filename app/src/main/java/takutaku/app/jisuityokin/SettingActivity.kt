package takutaku.app.jisuityokin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import takutaku.app.jisuityokin.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    private lateinit var dataStore: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStore = this.getSharedPreferences("DataStore", Context.MODE_PRIVATE)
        binding.saveEditText.setText(dataStore.getInt(Constants.SAVE_MONEY,0).toString())
        binding.goalEditText.setText(dataStore.getString(Constants.GOAL_TEXT, " "))
        binding.goalMoneyEditText.setText(dataStore.getInt(Constants.GOAL_MONEY,0).toString())

//        toolbarをActionBarとして扱う
        setSupportActionBar(binding.settingToolbar)
//        ActionBarの文字を削除
        supportActionBar!!.setDisplayShowTitleEnabled(false)
//        ActionBarに戻るボタンを実装
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)

        binding.settingSaveButton.setOnClickListener{
            val editor = dataStore.edit()
            editor.putInt(Constants.SAVE_MONEY, binding.saveEditText.text.toString().toInt())
            editor.putString(Constants.GOAL_TEXT, binding.goalEditText.text.toString())
            editor.putInt(Constants.GOAL_MONEY, binding.goalMoneyEditText.text.toString().toInt())
            editor.apply()
            val mainActivityIntent = Intent(applicationContext,MainActivity::class.java)
            startActivity(mainActivityIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val mainActivityIntent = Intent(applicationContext,MainActivity::class.java)
                startActivity(mainActivityIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}