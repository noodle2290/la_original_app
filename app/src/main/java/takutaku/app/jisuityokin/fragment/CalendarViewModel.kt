package takutaku.app.jisuityokin.fragment

import androidx.lifecycle.ViewModel
import java.util.*

class CalendarViewModel: ViewModel(){
    private val c: Calendar = Calendar.getInstance()
    var year = c.get(Calendar.YEAR)
    var month = c.get(Calendar.MONTH)
    var day = c.get(Calendar.DAY_OF_MONTH)
}