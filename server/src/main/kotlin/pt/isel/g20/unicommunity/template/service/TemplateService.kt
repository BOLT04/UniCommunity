package pt.isel.g20.unicommunity.template.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pt.isel.g20.unicommunity.repository.TemplateRepository
import pt.isel.g20.unicommunity.template.exception.CannotModifyTemplatesBeingUsedException
import pt.isel.g20.unicommunity.template.exception.NotFoundTemplateException
import pt.isel.g20.unicommunity.template.model.Template

@Service
class TemplateService(val templatesRepo: TemplateRepository) : ITemplateService {
    override fun getAllTemplates(): Iterable<Template> = templatesRepo.findAll()

    override fun getTemplateById(templateId: Long) = templatesRepo.findByIdOrNull(templateId) ?: throw NotFoundTemplateException()

    override fun createTemplate(name: String, hasForum: Boolean, blackboardNames: String): Template {

        val template = Template(name, hasForum, blackboardNames)

        return templatesRepo.save(template)
    }

    override fun editTemplate(templateId: Long, name: String, hasForum: Boolean?, blackboardNames: String?): Template {
        val template = getTemplateById(templateId)

        template.name = name

        if(template.boards.size != 0)
            throw CannotModifyTemplatesBeingUsedException()

        if(hasForum != null)
            template.hasForum = hasForum

        if(blackboardNames != null)
            template.blackboardNames = blackboardNames

        return templatesRepo.save(template)
    }

    override fun deleteTemplate(templateId: Long): Template {
        val template = getTemplateById(templateId)
        if(template.boards.size != 0)
            throw CannotModifyTemplatesBeingUsedException()

        templatesRepo.delete(template)
        return template
    }

}