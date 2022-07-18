package com.mahbub.bs23.utils

import android.widget.ImageView
import coil.load
import coil.transform.RoundedCornersTransformation

object Extensions {
    fun ImageView.loadImage(uri:String){
        load(uri){
            crossfade(true)
            crossfade(300)
            transformations(RoundedCornersTransformation(30f))
        }

    }
}