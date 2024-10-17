package com.kpaas.plog.util

class CalculateTimeDifference {
    fun formatTimeDifference(timeDifference: Long): String {
        val seconds = timeDifference / 1000
        val minutes = seconds / 60
        val hours = minutes / 60

        return String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60)
    }

    fun formatTime(timeString: String): String {
        val (hours, minutes, seconds) = timeString.split(":").map { it.toInt() }
        val totalSeconds = hours * 3600 + minutes * 60 + seconds

        return when {
            totalSeconds < 60 -> {
                "1분 미만"
            }

            totalSeconds < 3600 -> {
                "${minutes}분 ${seconds}초"
            }

            else -> {
                "${hours}시간 ${minutes}분 ${seconds}초"
            }
        }
    }

}