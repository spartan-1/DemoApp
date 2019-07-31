package com.pnr.demoapp.util.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
 * CustomRecyclerView
 */
class CustomRecyclerView : RecyclerView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle)

    /**
     * modifying scroll speed to improve scroll experience
     */
    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        val velocityYModified = velocityY / 2
        return super.fling(velocityX, velocityYModified)
    }

}
