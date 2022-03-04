package takutaku.app.jisuityokin

import android.app.Application
import androidx.room.Room

class MemoApplication : Application() {
    companion object {
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        db = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "memo_db")
            .allowMainThreadQueries()
            .build()
    }
}