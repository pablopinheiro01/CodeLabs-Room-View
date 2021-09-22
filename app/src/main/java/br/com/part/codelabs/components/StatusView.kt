package br.com.part.codelabs.components

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import br.com.part.codelabs.R
import br.com.part.codelabs.feature.data.entity.Status
import kotlin.math.absoluteValue

class StatusView(context: Context, attrs: AttributeSet?): AppCompatTextView(context, attrs) {

    fun setStatus(status: Status){
        val bg = when (status) {
            Status.TODO, Status.UNDEFINED -> R.drawable.rounded_blue
            Status.PROGRESS -> R.drawable.rounded_orange
            Status.DONE -> R.drawable.rounded_green
        }
        setBackgroundResource(bg)
    }

}