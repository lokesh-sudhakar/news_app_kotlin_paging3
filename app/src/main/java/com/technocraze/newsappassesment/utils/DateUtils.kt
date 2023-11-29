package com.technocraze.newsappassesment.utils

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {

  private const val INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
  private const val DISPLAY_FORMAT = "MMM dd, yyyy, h:mm a"

  fun formatData(dateString: String): String {
    val inputFormat = SimpleDateFormat(INPUT_FORMAT, Locale.US)
    val date = inputFormat.parse(dateString)
    val outputFormat = SimpleDateFormat(DISPLAY_FORMAT, Locale.US)
    return outputFormat.format(date)
  }
}