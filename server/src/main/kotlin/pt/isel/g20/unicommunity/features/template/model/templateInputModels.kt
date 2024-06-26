package pt.isel.g20.unicommunity.features.template.model

import com.fasterxml.jackson.annotation.JsonCreator

data class TemplateDto @JsonCreator constructor(
        val name: String,
        val hasForum: Boolean,
        val blackboardNames: String
)