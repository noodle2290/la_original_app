package takutaku.app.jisuityokin.ui.recommend

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import takutaku.app.jisuityokin.BuildConfig
import takutaku.app.jisuityokin.RecipeInterface
import takutaku.app.jisuityokin.databinding.FragmentRecommendBinding
import java.util.*

class RecommendFragment : Fragment() {

    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!
    private val browserIntent = Intent(Intent.ACTION_VIEW)
    private lateinit var recommendViewModel:RecommendViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val client = builderHttpClient() // OkHttpClient に logging の設定を追加

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://app.rakuten.co.jp/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        val recipeInterface: RecipeInterface = retrofit.create(RecipeInterface::class.java)
        recommendViewModel = ViewModelProvider(this)[RecommendViewModel::class.java]
        recipeApi(recipeInterface,recommendViewModel)

        binding.recipeIntentButton.setOnClickListener {
            startActivity(browserIntent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun builderHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(logging)
        }
        return client.build()
    }

    private fun recipeApi(recipeInterface:RecipeInterface,recommendViewModel:RecommendViewModel){
        runBlocking(Dispatchers.IO) {
            runCatching {
                recipeInterface.getRecipe(1073119324012769347,"json")
            }
        }.onSuccess {
            val size:Int = it.result.size
            val random = Random()
            recommendViewModel.recipeResult = it.result[random.nextInt(size)]
            binding.recipeTitleText.text = recommendViewModel.recipeResult!!.recipeTitle
            binding.timeTextView.text = recommendViewModel.recipeResult!!.recipeIndication
            binding.moneyTextView.text = recommendViewModel.recipeResult!!.recipeCost
            binding.recipeContentTextView.text = recommendViewModel.recipeResult!!.recipeDescription
            binding.recipeImage.load(recommendViewModel.recipeResult!!.foodImageUrl)
            browserIntent.data = Uri.parse(recommendViewModel.recipeResult!!.recipeUrl)
        }.onFailure {
            Toast.makeText(context,"失敗", Toast.LENGTH_SHORT).show()
        }
    }
}