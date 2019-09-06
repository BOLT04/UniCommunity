package pt.isel.g20.unicommunity.template.service

import pt.isel.g20.unicommunity.template.exception.NotFoundTemplateException
import pt.isel.g20.unicommunity.template.model.Template

interface ITemplateService {
    fun getAllTemplates() : Iterable<Template>

    @Throws(NotFoundTemplateException::class)
    fun getTemplateById(templateId: Long): Template

    fun createTemplate(name: String, hasForum: Boolean, blackboardNames: String): Template

    @Throws(NotFoundTemplateException::class)
    fun editTemplate(templateId: Long, hasForum: Boolean?, blackboardNames: String?): Template

    @Throws(NotFoundTemplateException::class)
    fun deleteTemplate(templateId: Long):Template
}