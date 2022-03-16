package takutaku.app.jisuityokin

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(
    version = 2,
    entities = [Memo.class],
autoMigrations = [
@AutoMigration (
    from = 1,
    to = 2
)
],
exportSchema = true
)
abstract class AppDatabase  : RoomDatabase() {
    @DeleteColumn(fromTableName = "Memo", deletedColumnName = "date")
    @DeleteColumn(fromTableName = "Memo", deletedColumnName = "check")
    class MyExampleAutoMigration : AutoMigrationSpec {
        @Override
        override fun onPostMigrate(db: SupportSQLiteDatabase) {
            // Invoked once auto migration is done
        }
    }
}

