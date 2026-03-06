package com.fibreuitv.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LiveTv
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fibreuitv.data.DemoRepository
import com.fibreuitv.model.ContentCard
import com.fibreuitv.model.NavItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun FibreTvApp() {
    val program = remember { DemoRepository.featuredProgram() }
    val rows = remember { DemoRepository.rows() }
    var selected by remember { mutableStateOf(NavItem.Home) }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF243B26), Color(0xFF101010), Color.Black)
                        )
                    )
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(28.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    item {
                        TopBar(selected = selected, onSelected = { selected = it })
                    }
                    item {
                        PreviewPane(programTitle = program.title, channel = program.channelName, time = "${program.startTime} - ${program.endTime}", description = program.description, tags = program.tags, upNext = program.upNextTitle, upNextTime = program.upNextTime)
                    }
                    items(rows) { row ->
                        ContentShelf(title = row.title, items = row.items)
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBar(selected: NavItem, onSelected: (NavItem) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(Color(0x55393939))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TopPillIcon(NavItem.Search, selected, onSelected, Icons.Outlined.Search)
            TopPillIcon(NavItem.Home, selected, onSelected, Icons.Outlined.Home)
            TopPillIcon(NavItem.LiveTv, selected, onSelected, Icons.Outlined.LiveTv)
            TopPillIcon(NavItem.Movies, selected, onSelected, Icons.Outlined.Movie)
            TopPillIcon(NavItem.Music, selected, onSelected, Icons.Outlined.MusicNote)
            TopPillIcon(NavItem.Settings, selected, onSelected, Icons.Outlined.Settings)
        }

        val now = LocalDateTime.now()
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0x55393939))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(now.format(DateTimeFormatter.ofPattern("HH:mm")), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 26.sp)
            Text(now.format(DateTimeFormatter.ofPattern("EEEE d MMMM")), color = Color(0xFFD6D6D6))
        }
    }
}

@Composable
private fun TopPillIcon(item: NavItem, selected: NavItem, onSelected: (NavItem) -> Unit, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(if (item == selected) Color(0xFFE5E5E5) else Color.Transparent)
            .size(42.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = item.title,
            tint = if (item == selected) Color.Black else Color.White,
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
private fun PreviewPane(
    programTitle: String,
    channel: String,
    time: String,
    description: String,
    tags: List<String>,
    upNext: String,
    upNextTime: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(Color(0x551A1A1A))
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(channel, color = Color(0xFF78B8FF), fontWeight = FontWeight.Bold)
            Text(programTitle, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 42.sp)
            Text(time, color = Color.White, modifier = Modifier.clip(RoundedCornerShape(18.dp)).background(Color(0xAA101010)).padding(horizontal = 14.dp, vertical = 4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                tags.forEach {
                    Text(it, color = Color(0xFFBFDFFF), modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(Color(0xFF1E4A88)).padding(horizontal = 10.dp, vertical = 4.dp))
                }
            }
            Text(description, color = Color(0xFFE6E6E6), maxLines = 2)
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column(modifier = Modifier.width(240.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("UP NEXT", color = Color(0xFFAEB4C3), fontWeight = FontWeight.Bold)
            Text(upNext, color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
            Text(upNextTime, color = Color(0xFFD7D7D7))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.horizontalGradient(listOf(Color(0xFF173A8E), Color(0xFF8A1D3B))))
            )
        }
    }
}

@Composable
private fun ContentShelf(title: String, items: List<ContentCard>) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 32.sp)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            items(items) { card ->
                Column(
                    modifier = Modifier
                        .width(280.dp)
                        .height(160.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF1C1C1E))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(card.title, color = Color.White, fontSize = 26.sp, maxLines = 1)
                        Text(card.subtitle, color = Color(0xFFBFBFBF), fontSize = 18.sp)
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(99.dp))
                            .background(Color(0xFF2F2F2F))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(card.progress.coerceIn(0f, 1f))
                                .height(6.dp)
                                .clip(RoundedCornerShape(99.dp))
                                .background(Color(0xFFB4D7FF))
                        )
                    }
                }
            }
        }
    }
}
