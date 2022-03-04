package takutaku.app.jisuityokin

import androidx.room.*

@Dao
interface MemoDao {
    @Query("SELECT * FROM memos")
    fun getAll(): List<Memo>

    @Query("SELECT * FROM memos where id = :id")
    fun getMemo(id: Int): Memo

    @Query("delete from memos")
    fun deleteAll()

    @Insert
    fun insert(user: Memo)

    @Update
    fun update(user: Memo)

    @Delete
    fun delete(user: Memo)
}