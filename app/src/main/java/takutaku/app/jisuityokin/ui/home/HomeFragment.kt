package takutaku.app.jisuityokin.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import takutaku.app.jisuityokin.Constants
import takutaku.app.jisuityokin.EditActivity
import takutaku.app.jisuityokin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var dataStore: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        dataStore = this.requireActivity().getSharedPreferences("DataStore", Context.MODE_PRIVATE)
        val goal = dataStore.getInt(Constants.GOAL_MONEY,0)
        val count = dataStore.getInt(Constants.COUNT_NUMBER,0)
        val save = dataStore.getInt(Constants.SAVE_MONEY,0)

        binding.timesNumberText.text = count.toString()
        binding.sumNumberText.text = (count * save).toString()
        binding.goalText.text = dataStore.getString(Constants.GOAL_TEXT, " ") + "まで"
        binding.goalNumberText.text = Math.ceil(((goal - count * save) / save.toDouble())).toInt().toString()
        binding.toaddButton.setOnClickListener {
            intentMethod(EditActivity())
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun intentMethod(activity: Activity, vararg ids:Int){
        val activityIntent = Intent(requireContext(), activity::class.java)
        for(i in ids) {
            activityIntent.putExtra(Constants.SELECTED_MEMO_ID, i)
        }
        startActivity(activityIntent)
    }
}