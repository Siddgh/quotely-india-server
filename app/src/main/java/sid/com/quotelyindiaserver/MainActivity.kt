package sid.com.quotelyindiaserver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import sid.com.quotelyindiaserver.model.QuotesMeta
import sid.com.quotelyindiaserver.utils.CSVReader
import sid.com.quotelyindiaserver.utils.FirestoreUtils
import sid.com.quotelyindiaserver.utils.RealtimeDatabaseUtils
import java.io.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun firestoreToDatabaseWriter(){
        FirestoreUtils.quotesCollectionReference.get().addOnSuccessListener {
            if (!it.isEmpty){
                it.documents.forEach {
                    val q = it.toObject(QuotesMeta::class.java)
                    RealtimeDatabaseUtils.writeLikes(q)
                }
            }
        }
    }

    fun printMovieAsId() {
        CSVReader.loopMovieNamesAndPrintAsId(
            resources.openRawResource(R.raw.quotes_data_full),
            1950,
            1998
        )
    }

    fun updateQuoteOfTheWeek(view: View) {
        val intent: Intent = Intent(applicationContext, ListOfMovies::class.java)
        startActivity(intent)
    }

    fun addMoviesToFirestore(view: View) {
        val inputStream: InputStream = resources.openRawResource(R.raw.quotes_data)
        CSVReader.addMoviesToFirestore(inputStream)
    }

    fun deleteQuotesFromFirestore(view: View) {
        FirestoreUtils.deleteQuotesFromFirestore("ramaramakyahaidramaticaa", 4943, 4956)
    }

    fun uploadCSVToFireStore(view: View) {

        // CURRENT-INDEX = sheeshmahal13970
        val inputStream: InputStream = resources.openRawResource(R.raw.quotes_data)
        CSVReader.writeDataToFireStore(inputStream, 13971) {
            Toast.makeText(this, "Uploading Quotes Completed", Toast.LENGTH_LONG).show()
        }

    }
}
