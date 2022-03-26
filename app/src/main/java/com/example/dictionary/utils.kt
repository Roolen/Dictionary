package com.example.dictionary

import android.content.Context
import android.graphics.Rect
import android.media.MediaPlayer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

open class LinearItemDecorations(
    sideMarginsDimension: Int? = null,
    marginBetweenElementsDimension: Int? = null,
    private val drawTopMarginForFirstElement: Boolean = true,
) : RecyclerView.ItemDecoration() {

    private val res = App.getInstance().resources
    private val sideMargins =
        if (sideMarginsDimension != null)
            res.getDimension(sideMarginsDimension).toInt()
        else 0
    private val verticalMargin =
        if (marginBetweenElementsDimension != null)
            res.getDimension(marginBetweenElementsDimension).toInt()
        else 0

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)
        outRect.set(
            sideMargins,
            if (drawTopMarginForFirstElement && position == 0) verticalMargin else 0,
            sideMargins,
            verticalMargin
        )
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun showToast(context: Context?, message: String?) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun dimen(@DimenRes id: Int): Float = App.getInstance().resources.getDimension(id)
fun str(@StringRes id: Int, vararg args: Any) = App.getInstance().getString(id, *args)
fun resInteger(@IntegerRes id: Int) = App.getInstance().resources.getInteger(id)

fun compatColor(@ColorRes id: Int) = ContextCompat.getColor(App.getInstance(), id)

fun playMp3(mp: MediaPlayer, media: ByteArray) {
    try {
        val temp = File.createTempFile("audioTemp", "mp3")
        temp.deleteOnExit()
        val fos = FileOutputStream(temp)
        fos.write(media)
        fos.close()

        mp.reset()

        val fis = FileInputStream(temp)
        mp.setDataSource(fis.fd)
        mp.prepare()
        mp.start()
    } catch (e: Exception) {
        Log.e("MediaError", e.message ?: "")
    }
}