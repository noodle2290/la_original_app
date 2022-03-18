package takutaku.app.jisuityokin

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeInterface {

    @GET("services/api/Recipe/CategoryRanking/20170426")
    suspend fun getRecipe(
        @Query("applicationId") id: Long,
        @Query("format") format:String
    ):Recipe
}