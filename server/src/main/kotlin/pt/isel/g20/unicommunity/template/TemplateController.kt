package pt.isel.g20.unicommunity.template

import org.springframework.http.CacheControl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.g20.unicommunity.common.NotFoundTemplateException
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.common.Uri.SINGLE_TEMPLATE_ROUTE
import pt.isel.g20.unicommunity.common.Uri.TEMPLATES_ROUTE
import pt.isel.g20.unicommunity.hateoas.CollectionObject
import pt.isel.g20.unicommunity.template.model.*
import pt.isel.g20.unicommunity.template.service.ITemplateService
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping(produces = ["application/json", "application/hal+json", "application/vnd.collection+json"])
class TemplateController(private val service: ITemplateService) {

    @GetMapping(path = [TEMPLATES_ROUTE])
    fun getAllTemplates() =
            service
                    .getAllTemplates()
                    .map(Template::toItemRepr)
                    .let {
                val response = CollectionObject(MultipleTemplatesResponse(it))
                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }

    @GetMapping(path = [SINGLE_TEMPLATE_ROUTE], produces = ["application/json"])
    fun getTemplateById(@PathVariable templateId: Long) =
            service.getTemplateById(templateId).let {
                val response = SingleTemplateResponse(it)

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }

    @PostMapping(path = [TEMPLATES_ROUTE], produces = ["application/json"])
    @ResponseStatus(HttpStatus.CREATED)
    fun createTemplate(@RequestBody templateDto: TemplateDto) =
            service.createTemplate(templateDto.name, templateDto.hasForum, templateDto.blackboardNames).let {
                val response = SingleTemplateResponse(it)

                ResponseEntity
                        .created(Uri.forSingleTemplateUri(it.id))
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }

    @PutMapping(path = [SINGLE_TEMPLATE_ROUTE], produces = ["application/json"])
    fun editTemplate(@PathVariable templateId: Long, @RequestBody templateDto: TemplateDto) =
            service.editTemplate(templateId, templateDto.name, templateDto.hasForum, templateDto.blackboardNames).let {
                val response = SingleTemplateResponse(it)

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }


    @DeleteMapping(path = [SINGLE_TEMPLATE_ROUTE], produces = ["application/json"])
    fun deleteTemplate(@PathVariable templateId: Long) =
            service.deleteTemplate(templateId).let {
                val response = SingleTemplateResponse(it)

                ResponseEntity
                        .ok()
                        .cacheControl(
                                CacheControl
                                        .maxAge(1, TimeUnit.HOURS)
                                        .cachePrivate())
                        .eTag(response.hashCode().toString())
                        .body(response)
            }
}