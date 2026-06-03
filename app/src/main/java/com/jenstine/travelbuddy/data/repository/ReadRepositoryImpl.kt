package com.jenstine.travelbuddy.data.repository

import com.jenstine.travelbuddy.domain.model.TravelArticle
import javax.inject.Inject

class ReadRepositoryImpl @Inject constructor() : ReadRepository {

    override suspend fun getArticles(): List<TravelArticle> = MOCK_ARTICLES

    companion object {
        private val MOCK_ARTICLES = listOf(
            TravelArticle(
                id = "1",
                title = "Ultimate Guide to Tokyo: Where Tradition Meets the Future",
                destination = "Tokyo, Japan",
                summary = "Navigate Tokyo's dizzying blend of ancient temples and neon-lit skyscrapers. From the serene Meiji Shrine to the electric buzz of Shibuya Crossing, discover why Tokyo tops every traveler's bucket list.",
                category = "Guide",
                readTimeMinutes = 8
            ),
            TravelArticle(
                id = "2",
                title = "10 Essential Tips for First-Time Backpackers in Southeast Asia",
                destination = "Southeast Asia",
                summary = "Everything you wish you knew before landing in Bangkok: how to negotiate tuk-tuk fares, which street foods are safe, how to spot tourist traps, and the unwritten etiquette rules that locals respect.",
                category = "Tips",
                readTimeMinutes = 5
            ),
            TravelArticle(
                id = "3",
                title = "Eating Your Way Through Marrakech's Medina",
                destination = "Marrakech, Morocco",
                summary = "The Djemaa el-Fna square transforms at sunset into the world's greatest open-air restaurant. Tangia, bastilla, msemen fresh from the griddle — this is a food pilgrimage unlike any other.",
                category = "Food",
                readTimeMinutes = 6
            ),
            TravelArticle(
                id = "4",
                title = "Patagonia on a Budget: Trekking the W Circuit",
                destination = "Patagonia, Chile",
                summary = "Torres del Paine's W Circuit is one of the world's great treks, but the prices can be alarming. Here's how to plan logistics, book refugios months ahead, and still experience the raw beauty of the Southern Andes.",
                category = "Adventure",
                readTimeMinutes = 10
            ),
            TravelArticle(
                id = "5",
                title = "The Real Bali: Beyond the Tourist Trail",
                destination = "Bali, Indonesia",
                summary = "Swap Seminyak's beach clubs for the misty highlands of Munduk and the sacred water temples of the east coast. The spiritual Bali that draws seekers still exists — you just have to know where to look.",
                category = "Culture",
                readTimeMinutes = 7
            ),
            TravelArticle(
                id = "6",
                title = "Iceland in Winter: Chasing the Northern Lights",
                destination = "Iceland",
                summary = "The aurora borealis doesn't follow a schedule, but a clear night away from Reykjavik's light pollution gives you a fighting chance. How to read the KP index and find dark-sky spots across the island.",
                category = "Guide",
                readTimeMinutes = 9
            ),
            TravelArticle(
                id = "7",
                title = "New York for New Yorkers: Neighborhoods Tourists Never See",
                destination = "New York, USA",
                summary = "Skip Times Square and head to Jackson Heights for the best Colombian food in America, or Rockaway Beach for a slice of New York summer that hasn't been Instagrammed to death.",
                category = "Tips",
                readTimeMinutes = 6
            ),
            TravelArticle(
                id = "8",
                title = "Santorini Without the Crowds: A Spring Visitor's Playbook",
                destination = "Santorini, Greece",
                summary = "May brings Santorini in bloom before the July–August crush. Where to stay outside Oia, which beaches the tour buses skip, and the quieter caldera villages where you can still find a taverna table at sunset.",
                category = "Guide",
                readTimeMinutes = 7
            )
        )
    }
}
