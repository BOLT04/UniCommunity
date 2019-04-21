package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import pt.isel.g20.unicommunity.template.model.Template

interface TemplateRepository : CrudRepository<Template, Long>
