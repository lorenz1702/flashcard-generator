data class SimpleBackCard(
    override val id: String,
    val back: String,
    val audiofront: String,
    val hint: String
) : Card()
