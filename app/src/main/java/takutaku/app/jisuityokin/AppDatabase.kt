package takutaku.app.jisuityokin

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    version = 2,
    entities = [Memo::class],
    autoMigrations = [
        AutoMigration (
            from = 1,
            to = 2,
            spec = AppDatabase.MyExampleAutoMigration::class
        )
    ],
    exportSchema = true
)
abstract class AppDatabase  : RoomDatabase() {
    @DeleteColumn(tableName = "memos", columnName = "date")
    @DeleteColumn(tableName = "memos", columnName = "check")
    class MyExampleAutoMigration : AutoMigrationSpec {
        @Override
        override fun onPostMigrate(db: SupportSQLiteDatabase) {
            // Invoked once auto migration is done
        }
    }
    abstract fun memoDao(): MemoDao
}
