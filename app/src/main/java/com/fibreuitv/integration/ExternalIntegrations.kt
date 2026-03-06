package com.fibreuitv.integration

import android.content.Context
import android.content.Intent
import android.net.Uri

object ExternalIntegrations {
    fun openYouTubeInSmartTube(context: Context, query: String) {
        val smartTubePackage = "com.teamsmart.videomanager.tv"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=$query"))
            .setPackage(smartTubePackage)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun openYouTubeMusic(context: Context, query: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://music.youtube.com/search?q=$query"))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun openRealDebridAuth(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://real-debrid.com/device"))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
