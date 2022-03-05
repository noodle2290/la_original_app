package takutaku.app.jisuityokin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import takutaku.app.jisuityokin.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        toolbarをActionBarとして扱う
        setSupportActionBar(binding.settingToolbar)

        //        ActionBarに戻るボタンを実装
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)
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