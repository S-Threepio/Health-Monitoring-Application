package com.swanand.mc

import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.IOException
import java.util.stream.IntStream

class HeartRateDetector(private val heartRateCallback: HeartRateCallback) : AsyncTask<Uri, Float, Int>() {
    override fun doInBackground(vararg uri: Uri): Int {
        try {
            val redIntensities = getFrameBits(uri[0])
            val windowSize = 30
            val movingWindowAverageRedIntensity = getMovingWindowAverage(
                redIntensities,
                windowSize
            )
            val prev = doubleArrayOf(1.0)
            val framesWithoutSignal = intArrayOf(0)
            return IntStream.range(1, movingWindowAverageRedIntensity!!.size - 1)
                .map { i ->
                    val slope =
                        (movingWindowAverageRedIntensity[i] - movingWindowAverageRedIntensity[i - 1]) *
                                (movingWindowAverageRedIntensity[i + 1] - movingWindowAverageRedIntensity[i])

                    if ((slope == 0.0 && prev[0] != 0.0) || (framesWithoutSignal[0] >= 30)) {
                        prev[0] = 0.0
                        framesWithoutSignal[0] = 0
                        return@map 1
                    } else if (slope == 0.0) {
                        framesWithoutSignal[0]++
                        return@map 0
                    }
                    prev[0] = slope
                    framesWithoutSignal[0] = if (slope < 0) 0 else (framesWithoutSignal[0] + 1)
                    return@map if (slope < 0) 1 else 0
                }
                .sum() * 60 / 45
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return -1
    }

    override fun onPostExecute(result: Int) {
        heartRateCallback.onHeartRateCalculated(result)
    }

     private fun getFrameBits(fileUri: Uri): List<Double> {
         val bitmaps = mutableListOf<Double>()
         val retriever = MediaMetadataRetriever()
         retriever.setDataSource(fileUri.path)
         val totalTime = 45
         for (time in 0 until totalTime * 1000000L step 33000) {
             val bitmap = retriever.getFrameAtTime(time, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
             if (bitmap != null) {
                 var redCount = 0.0
                 var pixelCount = 0
                 for (i in 0 until bitmap.width step 5) {
                     for (j in 0 until bitmap.height step 5) {
                         val pixel = bitmap.getPixel(i, j)
                         redCount += Color.red(pixel).toDouble()
                         pixelCount++
                     }
                 }
                 bitmaps.add(redCount / pixelCount)
//                    if (time % 990000 == 0L) {
//                        publishProgress((time.toDouble() / (totalTime * 1000000L)).toFloat())
//                    }
             }
         }
         return bitmaps
     }

 }
