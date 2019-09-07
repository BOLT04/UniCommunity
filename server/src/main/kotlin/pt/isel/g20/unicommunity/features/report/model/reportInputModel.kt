package pt.isel.g20.unicommunity.features.report.model

import com.fasterxml.jackson.annotation.JsonCreator

data class ReportDto @JsonCreator constructor(
        val userId: Long,
        val forumItemId: Long?,
        val commentId: Long?
)