package sid.com.quotelyindiaserver.utils

import android.util.Log
import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import sid.com.quotelyindiaserver.Constants
import sid.com.quotelyindiaserver.model.MovieMeta
import sid.com.quotelyindiaserver.model.QuotesMeta
import java.io.BufferedReader
import java.io.FileReader
import java.io.InputStream
import java.io.InputStreamReader
import sid.com.quotelyindiaserver.utils.StringUtils.movieNameAsId
import java.time.Year


object CSVReader {

    fun addMoviesToFirestore(inputStream: InputStream) {
        val bufferedReader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
        bufferedReader.forEachLine {
            var splitArray = it.split("|")
            Log.d(
                "MOVIE-DATA",
                "movieid=${splitArray[1].movieNameAsId()} | movie=${splitArray[1]} | year=${splitArray[0]}"
            )
            val data = MovieMeta(
                year = splitArray[0].trim().toInt(),
                movie = splitArray[1],
                movieid = splitArray[1].movieNameAsId()
            )
            FirestoreUtils.writeMoviesToFirestore(data)
        }
    }

    fun addTempMoviesToFirestore(inputStream: InputStream) {
        val movieArray = mutableListOf<MovieMeta>()
        val bufferedReader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
        var count = 1
        bufferedReader.forEachLine {
            var splitArray = it.split("|")
            Log.d(
                "MOVIE-DATA",
                "movieid=${splitArray[1].movieNameAsId()} | movie=${splitArray[1]} | year=${splitArray[0]}"
            )
            val data = MovieMeta(
                year = splitArray[0].trim().toInt(),
                movie = splitArray[1],
                movieid = splitArray[1].movieNameAsId()
            )
            RealtimeDatabaseUtils.writeRecentMovie(data, count.toString())
            Log.d(
                "RECENT-MOVIE-DATA",
                "$count : ${data.movie}"
            )
            count++
        }
    }

    fun writeDataToFireStore(inputStream: InputStream, index: Int, onComplete: () -> Unit) {
        val bufferedReader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
        var count: Int = index
        bufferedReader.forEachLine {
            var splitArray = it.split("|")
            val data: QuotesMeta =
                QuotesMeta(
                    year = splitArray[0].trim().toInt(),
                    movie = splitArray[1],
                    quotes = splitArray[2],
                    saidBy = splitArray[3],
                    tag = arrayListOf(splitArray[4]),
                    quoteId = splitArray[1].movieNameAsId() + count
                )

            FirestoreUtils.writeQuoteToFirestore(data) {}

            count += 1
        }
        onComplete()
    }

    fun loopMovieNamesAndPrintAsId(inputStream: InputStream, startYear: Int, endYear: Int) {
        val bufferedReader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
        var listMovieAsId = mutableListOf<String>()
        bufferedReader.forEachLine {
            var splitArray = it.split("|")
            listMovieAsId.add(splitArray[1].movieNameAsId())
            if (splitArray[0].trim().toInt() >= startYear && splitArray[0].trim().toInt() <= endYear) {
                Log.d("MOVIE-ID", splitArray[1].movieNameAsId())
            }
        }
        /*val set = HashSet(listMovieAsId)
        Log.d("MOVIE-ID", "Total Size is ${set.size}")
        for (s in set) {
            Log.d("MOVIE-ID", s)
        }*/
    }


}