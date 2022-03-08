package takutaku.app.jisuityokin

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "memos")
data class Memo(
    @PrimaryKey(autoGenerate = true)  val id: Int,
    val year: Int?,
    val month: Int?,
    val day: Int?,
    val check: Int?,
    val content: String?,
    )
