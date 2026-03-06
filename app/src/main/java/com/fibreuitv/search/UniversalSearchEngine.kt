package com.fibreuitv.search

import com.fibreuitv.data.DemoRepository
import com.fibreuitv.model.ContentCard

object UniversalSearchEngine {
    fun search(query: String): List<ContentCard> {
        if (query.isBlank()) return emptyList()
        val source = DemoRepository.rows().flatMap { it.items }
        return source.filter { item ->
            item.title.contains(query, ignoreCase = true) || item.subtitle.contains(query, ignoreCase = true)
        }
    }
}
