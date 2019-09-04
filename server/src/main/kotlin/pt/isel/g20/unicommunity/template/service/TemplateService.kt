package pt.isel.g20.unicommunity.template.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.common.NotFoundTemplateException
import pt.isel.g20.unicommunity.repository.TemplateRepository
import pt.isel.g20.unicommunity.template.model.Template

@Service
class TemplateService(val templatesRepo: TemplateRepository) {
    fun getAllTemplates(): Iterable<Template> = templatesRepo.findAll()

    fun getTemplateById(templateId: Long)
            = templatesRepo.findByIdOrNull(templateId) ?: throw NotFoundTemplateException()

    fun createTemplate(name: String, hasForum: Boolean, blackboardNames: String): Template {

        val template = Template(name, hasForum, blackboardNames)

        return templatesRepo.save(template)
    }

    fun editTemplate(templateId: Long, name: String, hasForum: Boolean?, blackboardNames: String?): Template {
        val template = getTemplateById(templateId)

        template.name = name

        if(hasForum != null)
            template.hasForum = hasForum

        if(blackboardNames != null)
            template.blackboardNames = blackboardNames

        return templatesRepo.save(template)
    }

    fun deleteTemplate(templateId: Long): Template {
        val template = getTemplateById(templateId)

        templatesRepo.delete(template)
        return template
    }

}