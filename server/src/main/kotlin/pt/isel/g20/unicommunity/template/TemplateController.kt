package pt.isel.g20.unicommunity.template

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.hateoas.Uri
import pt.isel.g20.unicommunity.hateoas.Uri.SINGLE_TEMPLATE_ROUTE
import pt.isel.g20.unicommunity.hateoas.Uri.TEMPLATES_ROUTE
import pt.isel.g20.unicommunity.template.exception.NotFoundTemplateException
import pt.isel.g20.unicommunity.template.model.MultipleTemplatesResponse
import pt.isel.g20.unicommunity.template.model.SingleTemplateResponse
import pt.isel.g20.unicommunity.template.model.Template
import pt.isel.g20.unicommunity.template.model.TemplateDto
import pt.isel.g20.unicommunity.template.service.ITemplateService
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping(produces = ["application/hal+json", "application/json", "application/vnd.collection+json"])
class TemplateController(private val service: ITemplateService) {

    @GetMapping(path = [TEMPLATES_ROUTE], produces = ["application/json"])
    fun getAllTemplates() =
            service.getAllTemplates().let {
                val templates = it.map { SingleTemplateResponse(it) }
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(templates.hashCode().toString())
                        .body(templates)
            }

    @GetMapping(path = [SINGLE_TEMPLATE_ROUTE], produces = ["application/json"])
    fun getTemplateById(@PathVariable templateId: Long) =
            service.getTemplateById(templateId).let {
                val singleTemplateResponse = SingleTemplateResponse(it)

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(singleTemplateResponse.hashCode().toString())
                        .body(singleTemplateResponse)
            }

    @PostMapping(path = [TEMPLATES_ROUTE], produces = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createTemplate(@RequestBody templateDto: TemplateDto) =
            service.createTemplate(templateDto.name, templateDto.hasForum, templateDto.blackboardNames).let {
                val singleTemplateResponse = SingleTemplateResponse(it)

                ResponseEntity
                        .created(Uri.forSingleTemplate(it.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(singleTemplateResponse.hashCode().toString())
                        .body(singleTemplateResponse)
            }

    @PutMapping(path = [SINGLE_TEMPLATE_ROUTE], produces = ["application/json"])
    fun editTemplate(@PathVariable templateId: Long, @RequestBody templateDto: TemplateDto) =
            service.editTemplate(templateId, templateDto.hasForum, templateDto.blackboardNames).let {
                val singleTemplateResponse = SingleTemplateResponse(it)

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(singleTemplateResponse.hashCode().toString())
                        .body(singleTemplateResponse)
            }


    @DeleteMapping(path = [SINGLE_TEMPLATE_ROUTE], produces = ["application/json"])
    fun deleteTemplate(@PathVariable templateId: Long) =
            service.deleteTemplate(templateId).let {
                val singleTemplateResponse = SingleTemplateResponse(it)

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(singleTemplateResponse.hashCode().toString())
                        .body(singleTemplateResponse)
            }


    @ExceptionHandler
    fun handleNotFoundTemplateException(e: NotFoundTemplateException) =
            ResponseEntity
                    .notFound()
                    .build<String>()
}