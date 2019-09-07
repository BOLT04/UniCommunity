package pt.isel.g20.unicommunity.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pt.isel.g20.unicommunity.features.template.model.Template
import javax.transaction.Transactional

@Repository
@Transactional
interface TemplateRepository : CrudRepository<Template, Long>{
    fun findByName(name: String?): Template?
}
