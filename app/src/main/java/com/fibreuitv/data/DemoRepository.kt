package com.fibreuitv.data

import com.fibreuitv.model.ContentCard
import com.fibreuitv.model.ContentRow
import com.fibreuitv.model.Program

object DemoRepository {
    fun featuredProgram() = Program(
        id = "epg_1",
        title = "Mickey's Great Clubhouse Hunt",
        description = "Mickey's clubhouse disappeared with friends inside. Solve clues to get everyone back together.",
        channelName = "Disney Jr",
        logoText = "DJR",
        startTime = "09:00",
        endTime = "09:50",
        tags = listOf("Kids", "S1", "E25", "HD"),
        upNextTitle = "Bluey",
        upNextTime = "09:50 - 09:55",
        progress = 0.65f
    )

    fun rows() = listOf(
        ContentRow(
            "Continue Watching",
            (1..10).map {
                ContentCard("cw_$it", "Episode $it", "Resume", progress = 0.1f * (it % 9))
            }
        ),
        ContentRow(
            "Favourite Channels",
            listOf("ITV1", "BBC One", "Sky Sports", "Discovery", "NatGeo", "TNT").mapIndexed { index, name ->
                ContentCard("fav_$index", name, "Live")
            }
        ),
        ContentRow(
            "My Watchlist",
            listOf("Dune", "The Truman Show", "Family Guy", "Planet Earth", "Loki", "The Last of Us").mapIndexed { index, title ->
                ContentCard("wl_$index", title, "Watch later")
            }
        ),
        ContentRow(
            "Suggestions (TMDB)",
            listOf("Severance", "Silo", "The Bear", "Inside Out 2", "Shogun", "Top Gun Maverick").mapIndexed { index, title ->
                ContentCard("sug_$index", title, "Recommended")
            }
        )
    )
}
