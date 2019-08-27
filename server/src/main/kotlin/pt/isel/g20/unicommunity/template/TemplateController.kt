package pt.isel.g20.unicommunity.template

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.common.*
import pt.isel.g20.unicommunity.common.Uri.SINGLE_TEMPLATE_ROUTE
import pt.isel.g20.unicommunity.common.Uri.TEMPLATES_ROUTE
import pt.isel.g20.unicommunity.common.presentation.AuthorizationRequired
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.template.model.*
import pt.isel.g20.unicommunity.template.service.ITemplateService

@RestController
@RequestMapping(produces = [APPLICATION_HAL_JSON, APPLICATION_JSON, APPLICATION_COLLECTION_JSON])
class TemplateController(private val service: ITemplateService) {

    @GetMapping(path = [TEMPLATES_ROUTE], produces = [APPLICATION_COLLECTION_JSON])
    fun getAllTemplates() =
            cacheOkResponse(
                    CollectionObject(
                            MultipleTemplatesResponse(
                                    service.getAllTemplates().map(Template::toItemRepr)
                            )
                    )
            )

    @GetMapping(path = [SINGLE_TEMPLATE_ROUTE], produces = [APPLICATION_JSON])
    fun getTemplateById(@PathVariable templateId: Long) =
            cacheOkResponse(SingleTemplateResponse(service.getTemplateById(templateId)))

    @AuthorizationRequired
    @PostMapping(path = [TEMPLATES_ROUTE], produces = [APPLICATION_JSON])
    @ResponseStatus(HttpStatus.CREATED)
    fun createTemplate(@RequestBody templateDto: TemplateDto) =
            service.createTemplate(
                    templateDto.name,
                    templateDto.hasForum,
                    templateDto.blackboardNames
            ).let {
                val responseBody = SingleTemplateResponse(it)
                val newResourceHref = Uri.forSingleTemplateUri(it.id)
                cacheCreatedResponse(responseBody, newResourceHref)
            }

    @AuthorizationRequired
    @PutMapping(path = [SINGLE_TEMPLATE_ROUTE], produces = [APPLICATION_JSON])
    fun editTemplate(@PathVariable templateId: Long, @RequestBody templateDto: TemplateDto) =
            cacheOkResponse(
                    SingleTemplateResponse(
                            service.editTemplate(
                                    templateId,
                                    templateDto.name,
                                    templateDto.hasForum,
                                    templateDto.blackboardNames
                            )
                    )
            )

    @AuthorizationRequired
    @DeleteMapping(path = [SINGLE_TEMPLATE_ROUTE], produces = [APPLICATION_JSON])
    fun deleteTemplate(@PathVariable templateId: Long) =
            cacheOkResponse(SingleTemplateResponse(service.deleteTemplate(templateId)))
}