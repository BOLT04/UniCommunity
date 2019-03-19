package pt.isel.g20.unicommunity.template.model

import com.fasterxml.jackson.annotation.JsonCreator

data class TemplateDto @JsonCreator constructor(val name: String, val modules: Array<String>) {
    fun toModel(): Template = Template(name, modules)
}