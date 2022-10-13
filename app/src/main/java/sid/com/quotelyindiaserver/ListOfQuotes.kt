package sid.com.quotelyindiaserver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import kotlinx.android.synthetic.main.activity_list_of_quotes.*
import sid.com.quotelyindiaserver.adaptors.QuotesAdapter
import sid.com.quotelyindiaserver.model.QuotesMeta
import sid.com.quotelyindiaserver.utils.FirestoreUtils

class ListOfQuotes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_quotes)

        val movieName = intent.getStringExtra(Constants.MOVIE_NAME)
        Log.d("INTENT-TRANSFER", "Movie Name : $movieName")

        val quotesAdapter: QuotesAdapter
        val options: FirestorePagingOptions<QuotesMeta> =
            FirestorePagingOptions.Builder<QuotesMeta>().setLifecycleOwner(this).setQuery(
                FirestoreUtils.getQuotesFromMovie(movieName ?: "Kesari"),
                FirestoreUtils.movieFetchConfig,
                QuotesMeta::class.java
            ).build()

        quotesAdapter = QuotesAdapter(options, this@ListOfQuotes)

        rv_listofquotes.apply {
            layoutManager =
                LinearLayoutManager(this@ListOfQuotes, LinearLayoutManager.VERTICAL, false)
            adapter = quotesAdapter
        }
    }
}
