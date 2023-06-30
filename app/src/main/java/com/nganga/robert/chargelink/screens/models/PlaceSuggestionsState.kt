package com.nganga.robert.chargelink.screens.models

import com.nganga.robert.chargelink.models.PlaceSuggestion

data class PlaceSuggestionsState(
    val query: String = "",
    val suggestions: List<PlaceSuggestion> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
