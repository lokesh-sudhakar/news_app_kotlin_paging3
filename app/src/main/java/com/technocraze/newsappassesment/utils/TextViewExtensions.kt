package com.technocraze.newsappassesment.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

fun TextView.makeHyperLink(url:String, displayText: String, context: Context){
  var clickableText = SpannableString(displayText).apply {
    val clickableSpan: ClickableSpan = object : ClickableSpan() {
      override fun onClick(textView: View) {
        context.startActivity(
          Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
          )
        )
      }

      override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = true
      }
    }
    setSpan(clickableSpan, 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
  }
  text = clickableText
  movementMethod = LinkMovementMethod.getInstance()
  highlightColor = Color.TRANSPARENT
  isClickable = true
}