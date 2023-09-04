package com.swanand.mc

import android.Manifest
import android.app.Activity
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import java.util.stream.Collectors
import java.util.stream.IntStream

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun permissionGiver(a: Activity) {
    val permissionToStore = ActivityCompat.checkSelfPermission(a, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    val externalStorePermission = 1
    ActivityCompat.requestPermissions(a, permissions, externalStorePermission)
}

fun getMovingWindowAverage(`val`: List<Double?>, windowSize: Int): List<Double>? {
    return IntStream.range(0, `val`.size - (windowSize - 1))
        .mapToObj { start: Int ->
            IntStream.range(start, start + windowSize).mapToDouble { i: Int ->
                `val`[i]!!
            }
                .sum() / windowSize
        }.collect(Collectors.toList())
}

