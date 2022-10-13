package sid.com.quotelyindiaserver.utils

import java.util.*

object StringUtils {

    fun String.movieNameAsId(): String {
        val movieId = this.replace("\\s".toRegex(), "")
            .toLowerCase(Locale.ROOT)
            .replace("(", "")
            .replace(")", "")
            .replace("-", "")
            .replace("_", "")
            .replace(".", "")
            .replace("*", "")
            .replace("&", "")
            .replace("%", "")
            .replace("$", "")
            .replace("#", "")
            .replace("@", "")
            .replace("!", "")
            .replace("+", "")
            .replace(":", "")
            .replace("'", "")
            .replace("?", "")
        return movieId
    }
}