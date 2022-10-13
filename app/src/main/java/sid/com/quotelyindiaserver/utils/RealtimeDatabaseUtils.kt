package sid.com.quotelyindiaserver.utils

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import sid.com.quotelyindiaserver.model.MovieMeta
import sid.com.quotelyindiaserver.model.QuotesMeta
import java.util.*

object RealtimeDatabaseUtils {
    private val firebaseDatabaseInstance: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance()
    }

    private val quoteoftheweekReference: DatabaseReference by lazy {
        firebaseDatabaseInstance.getReference("quoteoftheweek")
    }


    private val recentlyAddedMovieReference: DatabaseReference by lazy {
        firebaseDatabaseInstance.getReference("recent")
    }

    fun writeQuoteOfTheWeek(toWrite: QuotesMeta) {
        quoteoftheweekReference.setValue(toWrite)
    }

    fun writeRecentMovie(toWrite: MovieMeta, howRecent: String) {
        recentlyAddedMovieReference.child(howRecent).setValue(toWrite)
    }

    fun writeLikes(quotesMeta: QuotesMeta?){
        firebaseDatabaseInstance.getReference("likes").child(quotesMeta?.quoteId?:"").setValue("likes",0)
        Log.d("WRITING-LIKES", quotesMeta?.quoteId?:"YoYoYo")
    }

}