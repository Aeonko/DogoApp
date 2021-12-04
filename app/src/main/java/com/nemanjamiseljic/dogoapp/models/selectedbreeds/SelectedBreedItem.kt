package com.nemanjamiseljic.dogoapp.models.selectedbreeds

data class SelectedBreedItem(
    val breeds: List<Breed>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)