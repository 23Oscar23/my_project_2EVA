package es.otm.myproject.models

import com.google.gson.annotations.SerializedName

data class BreedResponsive(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String
)
