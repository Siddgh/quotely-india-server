package sid.com.quotelyindiaserver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import kotlinx.android.synthetic.main.activity_list_of_movies.*
import sid.com.quotelyindiaserver.adaptors.MovieNamesAdapter
import sid.com.quotelyindiaserver.model.MovieMeta
import sid.com.quotelyindiaserver.utils.FirestoreUtils


class ListOfMovies : AppCompatActivity() {

    private lateinit var movieAdapter: MovieNamesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_movies)

        val options: FirestorePagingOptions<MovieMeta> =
            FirestorePagingOptions.Builder<MovieMeta>().setLifecycleOwner(this).setQuery(
                FirestoreUtils.movieListQuery,
                FirestoreUtils.movieFetchConfig,
                MovieMeta::class.java
            ).build()

        movieAdapter = MovieNamesAdapter(options, this@ListOfMovies)

        rv_listofmovies.apply {
            layoutManager =
                LinearLayoutManager(this@ListOfMovies, LinearLayoutManager.VERTICAL, false)
            adapter = movieAdapter
        }

    }

}
