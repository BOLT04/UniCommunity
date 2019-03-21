package pt.isel.g20.unicommunity.template

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import pt.isel.g20.unicommunity.template.model.TemplateDto
import pt.isel.g20.unicommunity.template.model.TemplateLinksResponse
import pt.isel.g20.unicommunity.template.model.TemplateResponse
import pt.isel.g20.unicommunity.template.service.ITemplateService

private const val LIST_TEMPLATES_ROUTE = "/api/templates"
private const val GET_TEMPLATE_ROUTE = "/api/templates/{template-name}"

@RestController
class TemplateController(private val service: ITemplateService) {

    @GetMapping(path = [LIST_TEMPLATES_ROUTE])
    fun getAllTemplates() = service.getAllTemplates()

    @PostMapping(path = [LIST_TEMPLATES_ROUTE], produces = ["application/hal+json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createTemplate(@RequestBody templateDto: TemplateDto): TemplateLinksResponse {
        service.createTemplate(templateDto.toModel())

        val templateName = templateDto.name.replace(' ', '+')
        return TemplateLinksResponse(
                "$LIST_TEMPLATES_ROUTE/$templateName"
        )
    }

    @RequestMapping(GET_TEMPLATE_ROUTE, method = [RequestMethod.GET])
    fun getProjectByName(@PathVariable(value="template-name") name: String) : TemplateResponse {
        val template = service.getTemplateByName(name)
                ?: throw ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "A template with the given name couldn't be found.")

        return TemplateResponse(template.name, template.modules,
                "$LIST_TEMPLATES_ROUTE/${template.name}"
        )
    }
}