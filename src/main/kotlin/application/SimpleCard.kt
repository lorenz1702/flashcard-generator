data class SimpleCard(
    override val id: String,
    val front: String? = null, 
    val back: String,
    val audioback: String? = null,
    val audiofront: String? = null,
    val hint: String? = null
) : Card()
