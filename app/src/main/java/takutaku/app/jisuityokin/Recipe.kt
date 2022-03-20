package takutaku.app.jisuityokin

data class Recipe(
    val foodImageUrl: String,
    val mediumImageUrl: String,
    val nickname: String,
    val pickup: Int,
    val rank: String,
    val recipeCost: String,
    val recipeDescription: String,
    val recipeId: Long,
    val recipeIndication: String,
    val recipeMaterial: List<String>,
    val recipePublishday: String,
    val recipeTitle: String,
    val recipeUrl: String,
    val shop: Int,
    val smallImageUrl: String
)

data class Result(
    val result: List<Recipe>
)
