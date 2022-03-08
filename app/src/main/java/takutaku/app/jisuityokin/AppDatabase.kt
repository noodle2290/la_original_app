package takutaku.app.jisuityokin

import androidx.room.*

@Database(
    version = 2,
    entities = [Memo::class],
    autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ],
    exportSchema = true
)abstract class AppDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao
    @DeleteColumn(tableName = "Memo", columnName = "date")
}
