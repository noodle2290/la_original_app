package takutaku.app.jisuityokin.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import takutaku.app.jisuityokin.R

class CalendarFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    lateinit var viewModel: CalendarViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.run {
            viewModel = ViewModelProvider(this)[CalendarViewModel::class.java]
        }

        //カレンダーフラグメントでの初期日付を設定
        return DatePickerDialog(
            this.context as Context,
            this,
            viewModel.year,
            viewModel.month,
            viewModel.day)
    }

    //日付を設定した後の処理
    override fun onDateSet(view: android.widget.DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        viewModel.year = year
        viewModel.month = monthOfYear
        viewModel.day = dayOfMonth
        activity?.findViewById<Button>(R.id.date_button)?.text = "${viewModel.year}年${viewModel.month+1}月${viewModel.day}日"
    }
}