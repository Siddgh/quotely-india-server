package sid.com.quotelyindiaserver.adaptors

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import kotlinx.android.synthetic.main.layout_movie_names.view.*
import sid.com.quotelyindiaserver.Constants
import sid.com.quotelyindiaserver.ListOfQuotes
import sid.com.quotelyindiaserver.R
import sid.com.quotelyindiaserver.model.MovieMeta

class MovieNamesAdapter(options: FirestorePagingOptions<MovieMeta>, context: Context) :
    FirestorePagingAdapter<MovieMeta, MovieNamesAdapter.ViewHolder>(options) {

    val c = context

    class ViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_movie_names,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: MovieMeta) {
        holder.itemView.apply {
            tv_movie_names.text = model.movie
            Log.d("FIREBASE-READ", model.movie)
            setOnClickListener {
                val intent = Intent(c, ListOfQuotes::class.java)
                intent.putExtra(Constants.MOVIE_NAME, model.movie)
                c.startActivity(intent)
            }
        }
    }
}