package com.jenstine.travelKing.data.remote.model

import com.google.gson.annotations.SerializedName

// ── Request ──────────────────────────────────────────────────────────────────

data class GeminiRequest(
    @SerializedName("contents")         val contents: List<GeminiContent>,
    @SerializedName("generationConfig") val generationConfig: GeminiGenerationConfig? = null
)

data class GeminiContent(
    @SerializedName("parts") val parts: List<GeminiPart>,
    @SerializedName("role")  val role: String = "user"
)

data class GeminiPart(
    @SerializedName("text") val text: String
)

data class GeminiGenerationConfig(
    @SerializedName("maxOutputTokens") val maxOutputTokens: Int,
    @SerializedName("temperature")     val temperature: Float = 0.7f
)

// ── Response ─────────────────────────────────────────────────────────────────

data class GeminiResponse(
    @SerializedName("candidates") val candidates: List<GeminiCandidate> = emptyList()
)

data class GeminiCandidate(
    @SerializedName("content")      val content: GeminiContent,
    @SerializedName("finishReason") val finishReason: String? = null
)

// ── Parsed article DTO ───────────────────────────────────────────────────────

data class ArticleDto(
    @SerializedName("id")              val id: String = "",
    @SerializedName("title")           val title: String = "",
    @SerializedName("destination")     val destination: String = "",
    @SerializedName("summary")         val summary: String = "",
    @SerializedName("category")        val category: String = "Guide",
    @SerializedName("readTimeMinutes") val readTimeMinutes: Int = 5
)
