package pt.isel.g20.unicommunity.template.service

import pt.isel.g20.unicommunity.template.model.Template

interface ITemplateService {
    fun getAllTemplates() : Collection<Template>
    fun createTemplate(template: Template)
    fun getTemplateByName(templateName: String): Template?
}