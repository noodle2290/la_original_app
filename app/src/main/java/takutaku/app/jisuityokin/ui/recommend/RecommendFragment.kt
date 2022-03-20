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

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recommendViewModel =
            ViewModelProvider(this)[RecommendViewModel::class.java]

        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val client = builderHttpClient() // OkHttpClient に logging の設定を追加
        val random = Random()
        val browserIntent = Intent(Intent.ACTION_VIEW)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://app.rakuten.co.jp/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        val recipeInterface: RecipeInterface = retrofit.create(RecipeInterface::class.java)

        runBlocking(Dispatchers.IO) {
            runCatching {
                recipeInterface.getRecipe(1073119324012769347,"json")
            }
        }.onSuccess {
            val size:Int = it.result.size
            val recipeResult = it.result[random.nextInt(size)]

            binding.recipeTitleText.text = recipeResult.recipeTitle
            binding.timeTextView.text = recipeResult.recipeIndication
            binding.moneyTextView.text = recipeResult.recipeCost
            binding.recipeContentTextView.text = recipeResult.recipeDescription
            binding.recipeImage.load(recipeResult.foodImageUrl)
            browserIntent.data = Uri.parse(recipeResult.recipeUrl)
        }.onFailure {
            Toast.makeText(context,"失敗", Toast.LENGTH_SHORT).show()
        }

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
}