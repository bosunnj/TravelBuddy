package com.jenstine.travelKing.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jenstine.travelKing.domain.model.AppTheme
import com.jenstine.travelKing.domain.model.DistanceUnit

private val CURRENCIES = listOf("USD", "EUR", "GBP", "JPY", "AUD", "CAD", "SGD")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    // Local draft for the API key — only persisted when the user taps Save
    var apiKeyDraft by rememberSaveable(settings.aiApiKey) { mutableStateOf(settings.aiApiKey) }
    var apiKeyVisible by remember { mutableStateOf(false) }
    var currencyExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // ── Appearance ──────────────────────────────────────────────────────
        SectionHeader("Appearance")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Theme", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            AppTheme.entries.forEachIndexed { index, theme ->
                SegmentedButton(
                    selected = settings.theme == theme,
                    onClick = { viewModel.setTheme(theme) },
                    shape = SegmentedButtonDefaults.itemShape(index, AppTheme.entries.size)
                ) { Text(theme.label) }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()

        // ── Travel AI ────────────────────────────────────────────────────────
        Spacer(modifier = Modifier.height(16.dp))
        SectionHeader("Travel AI")
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Gemini API key for AI-generated travel content (Read tab). Get a free key at aistudio.google.com.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = apiKeyDraft,
            onValueChange = { apiKeyDraft = it },
            label = { Text("Gemini API Key") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (apiKeyVisible) VisualTransformation.None
                                   else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { apiKeyVisible = !apiKeyVisible }) {
                    Icon(
                        imageVector = if (apiKeyVisible) Icons.Filled.VisibilityOff
                                      else Icons.Filled.Visibility,
                        contentDescription = if (apiKeyVisible) "Hide key" else "Show key"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.setAiApiKey(apiKeyDraft) },
            modifier = Modifier.fillMaxWidth(),
            enabled = apiKeyDraft != settings.aiApiKey
        ) { Text("Save API Key") }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()

        // ── Preferences ──────────────────────────────────────────────────────
        Spacer(modifier = Modifier.height(16.dp))
        SectionHeader("Preferences")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Distance units", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            DistanceUnit.entries.forEachIndexed { index, unit ->
                SegmentedButton(
                    selected = settings.distanceUnit == unit,
                    onClick = { viewModel.setDistanceUnit(unit) },
                    shape = SegmentedButtonDefaults.itemShape(index, DistanceUnit.entries.size)
                ) { Text(unit.label) }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        ExposedDropdownMenuBox(
            expanded = currencyExpanded,
            onExpandedChange = { currencyExpanded = it }
        ) {
            OutlinedTextField(
                value = settings.currency,
                onValueChange = {},
                readOnly = true,
                label = { Text("Currency") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(currencyExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
            )
            ExposedDropdownMenu(
                expanded = currencyExpanded,
                onDismissRequest = { currencyExpanded = false }
            ) {
                CURRENCIES.forEach { currency ->
                    DropdownMenuItem(
                        text = { Text(currency) },
                        onClick = {
                            viewModel.setCurrency(currency)
                            currencyExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()

        // ── About ────────────────────────────────────────────────────────────
        Spacer(modifier = Modifier.height(16.dp))
        SectionHeader("About")
        Spacer(modifier = Modifier.height(8.dp))
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("TravelKing", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Version 1.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Your AI-powered travel companion for discovering destinations, planning trips, and journaling your adventures.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors()
            ) { Text("Done") }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary
    )
}
