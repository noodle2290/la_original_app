package takutaku.app.jisuityokin

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MemoApplication : Application() {
    companion object {
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        db = Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, "memo_db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration() //既存のデータが失われるのを許容する
            .build()
    }
}