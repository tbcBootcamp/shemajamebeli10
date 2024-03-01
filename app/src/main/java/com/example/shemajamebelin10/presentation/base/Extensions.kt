package com.example.shemajamebelin10.presentation.base

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun ImageView.loadImagesWithGlide(drawable: Int) {
    Glide.with(this)
        .load(drawable)
        .into(this)
}