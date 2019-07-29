package com.pnr.demoapp.ui.screens.imageviewer

import android.os.Bundle
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.pnr.demoapp.R
import com.pnr.demoapp.base.BaseActivity
import com.pnr.demoapp.util.app.constants.AppConstants
import com.pnr.demoapp.util.glide.GlideApp

/**
 * ImageViewerActivity
 */
class ImageViewerActivity : BaseActivity() {

    /**
     * PhotoView library is used
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)
        val url = intent.getStringExtra(AppConstants.IMAGE_URL_KEY)
        GlideApp.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.glide_default)
            .into(findViewById(R.id.image_full_screen))
    }
}