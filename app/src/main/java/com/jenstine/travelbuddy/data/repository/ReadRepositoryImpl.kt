package com.jenstine.travelKing.data.repository

import com.google.gson.Gson
import com.jenstine.travelKing.data.remote.api.GeminiApiService
import com.jenstine.travelKing.data.remote.model.ArticleDto
import com.jenstine.travelKing.data.remote.model.GeminiContent
import com.jenstine.travelKing.data.remote.model.GeminiGenerationConfig
import com.jenstine.travelKing.data.remote.model.GeminiPart
import com.jenstine.travelKing.data.remote.model.GeminiRequest
import com.jenstine.travelKing.domain.model.TravelArticle
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.util.UUID
import javax.inject.Inject

/** Thrown only for auth failures (401/403) that require the user to fix their key. */
class ApiAuthException(message: String) : Exception(message)

class ReadRepositoryImpl @Inject constructor(
    private val apiService: GeminiApiService,
    private val settingsRepository: SettingsRepository,
    private val gson: Gson
) : ReadRepository {

    // Survives the ViewModel lifetime; cleared only on explicit refresh
    private var cache: List<TravelArticle>? = null

    override suspend fun getArticles(): List<TravelArticle> {
        val apiKey = settingsRepository.settings.first().aiApiKey.trim()
        if (apiKey.isBlank()) return MOCK_ARTICLES

        // Return cache immediately if available (no API call on re-entry)
        cache?.let { return it }

        return try {
            fetchFromGemini(apiKey).also { cache = it }
        } catch (e: ApiAuthException) {
            throw e                    // 401/403 — user must fix their key
        } catch (e: Exception) {
            MOCK_ARTICLES              // 429/network/parse — show sample content silently
        }
    }

    override suspend fun refreshArticles(): List<TravelArticle> {
        val apiKey = settingsRepository.settings.first().aiApiKey.trim()
        if (apiKey.isBlank()) return MOCK_ARTICLES

        return try {
            fetchFromGemini(apiKey).also { cache = it }
        } catch (e: ApiAuthException) {
            throw e
        } catch (e: Exception) {
            cache ?: MOCK_ARTICLES     // on rate-limit: keep old cache if we have it
        }
    }

    private suspend fun fetchFromGemini(apiKey: String): List<TravelArticle> {
        try {
            val response = apiService.generateContent(
                apiKey = apiKey,
                model = MODEL,
                request = GeminiRequest(
                    contents = listOf(
                        GeminiContent(parts = listOf(GeminiPart(ARTICLE_PROMPT)))
                    ),
                    generationConfig = GeminiGenerationConfig(maxOutputTokens = 2048)
                )
            )

            val raw = response.candidates
                .firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: return MOCK_ARTICLES

            val json = raw.trim()
                .removePrefix("```json").removePrefix("```")
                .removeSuffix("```").trim()

            return gson.fromJson(json, Array<ArticleDto>::class.java).map { dto ->
                TravelArticle(
                    id              = dto.id.ifBlank { UUID.randomUUID().toString() },
                    title           = dto.title,
                    destination     = dto.destination,
                    summary         = dto.summary,
                    category        = dto.category,
                    readTimeMinutes = dto.readTimeMinutes.coerceIn(1, 30)
                )
            }
        } catch (e: HttpException) {
            throw when (e.code()) {
                401  -> ApiAuthException("Invalid API key. Re-enter your Gemini key in ⚙ Settings.")
                403  -> ApiAuthException("API key lacks permission. Ensure it is a Gemini API key from aistudio.google.com.")
                429  -> Exception("Rate limit reached.")
                else -> Exception("Gemini API error ${e.code()}.")
            }
        }
    }

    companion object {
        private const val MODEL = "gemini-2.0-flash-lite"

        private const val ARTICLE_PROMPT = """Generate 6 engaging travel articles as a JSON array. Mix a variety of global destinations and travel styles.
Each object must have exactly these keys:
  "id"              - unique string "1" through "6"
  "title"           - compelling article title (max 65 chars)
  "destination"     - "City, Country" or region name
  "summary"         - 2 to 3 vivid sentences describing the destination or travel topic
  "category"        - exactly one of: Guide, Tips, Food, Culture, Adventure
  "readTimeMinutes" - integer from 4 to 10

Return ONLY the raw JSON array. No markdown fences, no explanation, no trailing text."""

        val MOCK_ARTICLES = listOf(
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
