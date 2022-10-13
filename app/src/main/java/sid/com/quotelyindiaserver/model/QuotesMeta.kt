package sid.com.quotelyindiaserver.model

data class QuotesMeta(
    val year: Int = 0,
    val movie: String = "",
    val quotes: String = "",
    val saidBy: String = "",
    val quoteId: String = "",
    val tag: List<String> = mutableListOf()
) {

}