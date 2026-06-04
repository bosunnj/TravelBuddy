package com.jenstine.travelKing.data.remote.model

import com.google.gson.annotations.SerializedName

// ── Request ──────────────────────────────────────────────────────────────────

data class AnthropicRequest(
    @SerializedName("model")      val model: String,
    @SerializedName("max_tokens") val maxTokens: Int,
    @SerializedName("messages")   val messages: List<AnthropicMessage>
)

data class AnthropicMessage(
    @SerializedName("role")    val role: String,
    @SerializedName("content") val content: String
)

// ── Response ─────────────────────────────────────────────────────────────────

data class AnthropicResponse(
    @SerializedName("id")          val id: String = "",
    @SerializedName("content")     val content: List<ContentBlock> = emptyList(),
    @SerializedName("stop_reason") val stopReason: String? = null
)

data class ContentBlock(
    @SerializedName("type") val type: String = "",
    @SerializedName("text") val text: String? = null
)

// ── Parsed article DTO ───────────────────────────────────────────────────────

data class ArticleDto(
    @SerializedName("id")               val id: String = "",
    @SerializedName("title")            val title: String = "",
    @SerializedName("destination")      val destination: String = "",
    @SerializedName("summary")          val summary: String = "",
    @SerializedName("category")         val category: String = "Guide",
    @SerializedName("readTimeMinutes")  val readTimeMinutes: Int = 5
)
