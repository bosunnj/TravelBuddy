package com.jenstine.travelKing.data.repository

import com.jenstine.travelKing.domain.model.TravelDestination
import javax.inject.Inject

class SeeRepositoryImpl @Inject constructor() : SeeRepository {

    override suspend fun getDestinations(): List<TravelDestination> = MOCK_DESTINATIONS

    companion object {
        private val MOCK_DESTINATIONS = listOf(
            TravelDestination(
                id = "1", name = "Tokyo", country = "Japan",
                description = "A dizzying blend of ancient temples and neon-lit skyscrapers.",
                imageUrl = "https://picsum.photos/seed/tokyo/400/300",
                latitude = 35.6762, longitude = 139.6503
            ),
            TravelDestination(
                id = "2", name = "Paris", country = "France",
                description = "The city of light, art, cuisine, and the iconic Eiffel Tower.",
                imageUrl = "https://picsum.photos/seed/paris/400/300",
                latitude = 48.8566, longitude = 2.3522
            ),
            TravelDestination(
                id = "3", name = "Bali", country = "Indonesia",
                description = "Lush terraced rice paddies, sacred temples, and spiritual culture.",
                imageUrl = "https://picsum.photos/seed/bali/400/300",
                latitude = -8.4095, longitude = 115.1889
            ),
            TravelDestination(
                id = "4", name = "New York", country = "USA",
                description = "The city that never sleeps — boroughs, skylines, and endless energy.",
                imageUrl = "https://picsum.photos/seed/newyork/400/300",
                latitude = 40.7128, longitude = -74.0060
            ),
            TravelDestination(
                id = "5", name = "Patagonia", country = "Chile",
                description = "Raw Andean wilderness, glaciers, and the legendary W Circuit trek.",
                imageUrl = "https://picsum.photos/seed/patagonia/400/300",
                latitude = -50.9423, longitude = -73.4068
            ),
            TravelDestination(
                id = "6", name = "Santorini", country = "Greece",
                description = "Whitewashed clifftop villages, blue domes, and caldera sunsets.",
                imageUrl = "https://picsum.photos/seed/santorini/400/300",
                latitude = 36.3932, longitude = 25.4615
            ),
            TravelDestination(
                id = "7", name = "Marrakech", country = "Morocco",
                description = "A sensory labyrinth of souks, riads, and saffron-scented cuisine.",
                imageUrl = "https://picsum.photos/seed/marrakech/400/300",
                latitude = 31.6295, longitude = -7.9811
            ),
            TravelDestination(
                id = "8", name = "Reykjavik", country = "Iceland",
                description = "Gateway to volcanoes, geysers, the midnight sun, and northern lights.",
                imageUrl = "https://picsum.photos/seed/reykjavik/400/300",
                latitude = 64.1466, longitude = -21.9426
            ),
            TravelDestination(
                id = "9", name = "Machu Picchu", country = "Peru",
                description = "The lost Incan citadel perched high in the cloud-wrapped Andes.",
                imageUrl = "https://picsum.photos/seed/machupicchu/400/300",
                latitude = -13.1631, longitude = -72.5450
            ),
            TravelDestination(
                id = "10", name = "Serengeti", country = "Tanzania",
                description = "Witness the Great Migration across endless golden savanna.",
                imageUrl = "https://picsum.photos/seed/serengeti/400/300",
                latitude = -2.3333, longitude = 34.8333
            )
        )
    }
}

