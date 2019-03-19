package pt.isel.g20.unicommunity.template.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.template.model.Template

@Service
class TemplateService : ITemplateService {
    val templates = hashMapOf<String, Template>()

    private val logger = LoggerFactory.getLogger(TemplateService::class.java)

    @Synchronized
    override fun getAllTemplates() = templates.values

    @Synchronized
    override fun createTemplate(template: Template) {
        templates[template.name] = template
    }

    override fun getTemplateByName(templateName: String) = templates[templateName]
}