package takutaku.app.jisuityokin

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    version = 4,
    entities = [Memo::class],
    autoMigrations = [
        AutoMigration (
            from = 3,
            to = 4,
            spec = AppDatabase.MyExampleAutoMigration::class
        )
    ],
    exportSchema = true
)
abstract class AppDatabase  : RoomDatabase() {
    @DeleteColumn(tableName = "memos", columnName = "check")
    class MyExampleAutoMigration : AutoMigrationSpec {
        @Override
        override fun onPostMigrate(db: SupportSQLiteDatabase) {
            // Invoked once auto migration is done
        }
    }
    abstract fun memoDao(): MemoDao
}
