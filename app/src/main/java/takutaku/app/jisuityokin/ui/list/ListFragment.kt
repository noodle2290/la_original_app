package takutaku.app.jisuityokin.ui.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import takutaku.app.jisuityokin.*
import takutaku.app.jisuityokin.databinding.FragmentListBinding

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    private val memoDao = MemoApplication.db.memoDao()

    private lateinit var memoAdapter: MemoAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(ListViewModel::class.java)

        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        memoAdapter = MemoAdapter(object : OnItemClickListener {
            // 詳細画面に遷移
            override fun onItemClick(memo: Memo) {
                intentMethod(EditActivity(),memo.id)
            }
        })

        // RecyclerViewの設定
        binding.recyclerView.adapter = memoAdapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        dashboardViewModel.text.observe(viewLifecycleOwner) {
        }

        binding.toaddButton2.setOnClickListener{
            intentMethod(EditActivity())
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        // 保存されているメモを取得
        val memoList: List<Memo> = memoDao.getAll()
        memoAdapter.submitList(memoList)
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