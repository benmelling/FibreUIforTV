package com.fibreuitv.model

enum class NavItem(val title: String) {
    Search("Search"),
    Home("Home"),
    LiveTv("Live TV"),
    Movies("Movies"),
    Series("Series"),
    YouTube("YouTube"),
    Music("Music"),
    Recordings("Recordings"),
    Settings("Settings")
}

data class Program(
    val id: String,
    val title: String,
    val description: String,
    val channelName: String,
    val logoText: String,
    val startTime: String,
    val endTime: String,
    val tags: List<String>,
    val upNextTitle: String,
    val upNextTime: String,
    val progress: Float
)

data class ContentCard(
    val id: String,
    val title: String,
    val subtitle: String,
    val progress: Float = 0f
)

data class ContentRow(
    val title: String,
    val items: List<ContentCard>
)
