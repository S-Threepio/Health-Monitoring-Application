package com.swanand.mc

import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log

class HeartRateCalculator(private val videoUri: Uri) {

    private val frameInterval = 5
    private val frameRangeX = 550..650
    private val frameRangeY = 550..650
    private val peakThreshold = 3500

    fun calculateHeartRate():Int{
        val retriever = MediaMetadataRetriever()
        try {

            retriever.setDataSource(videoUri.path)

            val frameCount =
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_FRAME_COUNT)
                    ?.toInt() ?: 0

            val frameList = extractFrames(retriever, frameCount)

            val redIntensities = calculateRedIntensities(frameList)
            val movingAverages = calculateMovingAverages(redIntensities)

            val heartRate = calculateHeartRateFromMovingAverages(movingAverages)
            return heartRate
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("HeartRateCalculator", "Error calculating heart rate: ${e.message}")
        } finally {
            retriever.release()
        }
        return -1
    }

    private fun extractFrames(retriever: MediaMetadataRetriever, frameCount: Int): List<Bitmap> {
        val frameList = mutableListOf<Bitmap>()
        for (i in 10 until frameCount step frameInterval) {
            val bitmap = retriever.getFrameAtIndex(i) ?: continue
            frameList.add(bitmap)
        }
        return frameList
    }

    private fun calculateRedIntensities(frameList: List<Bitmap>): List<Long> {
        val redIntensities = mutableListOf<Long>()
        for (frame in frameList) {
            var redBucket: Long = 0
            var pixelCount: Long = 0
            for (y in frameRangeY) {
                for (x in frameRangeX) {
                    val color = frame.getPixel(x, y)
                    pixelCount++
                    redBucket += Color.red(color).toLong()
                }
            }
            redIntensities.add(redBucket)
        }
        return redIntensities
    }

    private fun calculateMovingAverages(redIntensities: List<Long>): List<Long> {
        val movingAverages = mutableListOf<Long>()
        for (i in 0 until redIntensities.size - 5) {
            val temp = (redIntensities.slice(i..i + 4).sum()) / 5
            movingAverages.add(temp)
        }
        return movingAverages
    }

    private fun calculateHeartRateFromMovingAverages(movingAverages: List<Long>): Int {
        var count = 0
        var prevValue = movingAverages[0]
        for (value in movingAverages) {
            if (value - prevValue > peakThreshold) {
                count++
            }
            prevValue = value
        }
        return ((count.toFloat() / movingAverages.size) * 60).toInt()
    }
}
