package sid.com.quotelyindiaserver.utils

import android.util.Log
import androidx.paging.PagedList
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.*
import sid.com.quotelyindiaserver.Constants
import sid.com.quotelyindiaserver.model.MovieMeta
import sid.com.quotelyindiaserver.model.QuotesMeta

object FirestoreUtils {
    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    val quotesCollectionReference: CollectionReference by lazy {
        firestoreInstance.collection(
            "quotes"
        )
    }
    val moviesCollectionReference: CollectionReference by lazy {
        firestoreInstance.collection("movies")
    }

    val movieListQuery: Query by lazy {
        moviesCollectionReference.orderBy("year",Query.Direction.DESCENDING)
    }

    val movieFetchConfig: PagedList.Config by lazy {
        PagedList.Config.Builder().setEnablePlaceholders(false).setPrefetchDistance(10).setPageSize(5).build()
    }

    fun getQuotesFromMovie(movieName: String):Query{
        return quotesCollectionReference.whereEqualTo("movie",movieName)
    }

    val quotesFetchConfig: PagedList.Config by lazy {
        PagedList.Config.Builder().setEnablePlaceholders(false).setPrefetchDistance(10).setPageSize(5).build()
    }


    fun writeMoviesToFirestore(movieMeta: MovieMeta) {
        moviesCollectionReference.document(movieMeta.movieid).set(movieMeta).addOnSuccessListener {
            Log.d("FIREBASE-WRITE", "Firebase Write ${movieMeta.movieid}")
        }.addOnFailureListener { e ->
            Log.w(Constants.FIREBASE_ERROR_LOGS, "Error Writing Documents to Movies", e)
        }
    }

    fun writeQuoteToFirestore(quotesMeta: QuotesMeta, onComplete: () -> Unit) {
        quotesCollectionReference.document(quotesMeta.quoteId).set(quotesMeta)
            .addOnSuccessListener {
                Log.d("FIREBASE-WRITE", "Firebase Write ${quotesMeta.quoteId}")
                onComplete()
            }
            .addOnFailureListener { e ->
                Log.w(
                    Constants.FIREBASE_ERROR_LOGS,
                    "Error writing document",
                    e
                )
            }
    }

    fun deleteQuotesFromFirestore(documentId: String, startIndex: Int, endIndex: Int) {
        for (index in startIndex..endIndex) {
            firestoreInstance.collection("quotes").document(documentId + index).delete()
                .addOnSuccessListener {
                    Log.d("DOC-DELETE", "${documentId + index} Document Deleted!")
                }
        }
    }
}