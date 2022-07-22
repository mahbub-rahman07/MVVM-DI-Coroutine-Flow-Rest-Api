package com.mahbub.rest_mvvm.utils

import android.widget.ImageView
import coil.load
import coil.transform.RoundedCornersTransformation

object Extensions {
    fun ImageView.loadImage(uri:String, placeholder:Int){
        load(uri){
            placeholder(placeholder)
            crossfade(true)
            crossfade(300)
            transformations(RoundedCornersTransformation(30f))
        }

    }
}