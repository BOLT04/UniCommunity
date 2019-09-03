package pt.isel.g20.unicommunity.report.model

import pt.isel.g20.unicommunity.comment.model.PartialCommentObject
import pt.isel.g20.unicommunity.common.Rels
import pt.isel.g20.unicommunity.common.Uri
import pt.isel.g20.unicommunity.forumItem.model.PartialForumItemObject
import pt.isel.g20.unicommunity.hateoas.*
import pt.isel.g20.unicommunity.user.model.PartialUserObject

class SingleReportResponse(report: Report) : HalObject(mutableMapOf(), mutableMapOf()) {
    val id : Long = report.id
    val forumItemId: Long? = report.forumItem?.id
    val commentId: Long? = report.comment?.id
    val numberOfReports = report.numberOfReports

    init {
        val comment = report.comment
        val forumItem = report.forumItem

        if(comment != null) {
            val partialComment = PartialCommentObject(
                    comment.content,
                    comment.author.name,
                    comment.createdAt.toString(),
                    mapOf("self" to Link(Uri.forSingleCommentText(
                            comment.forumItem.forum.id,
                            comment.forumItem.id,
                            comment.id
                    )))
            )
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_SINGLE_COMMENT to partialComment
            ))
        }
        else if(forumItem != null){
            val partialForumItem = PartialForumItemObject(
                    forumItem.name,
                    forumItem.content,
                    forumItem.author.name,
                    forumItem.createdAt.toString(),
                    mapOf("self" to Link(Uri.forSingleForumItemText(
                            forumItem.forum.id,
                            forumItem.id
                    )))
            )
            super._embedded?.putAll(sequenceOf(
                    Rels.GET_SINGLE_FORUMITEM to partialForumItem
            ))
        }

        val partialUser = PartialUserObject(
                report.user.name,
                report.user.email,
                mapOf("self" to Link(Uri.forSingleUserText(report.user.id)))
        )
        super._embedded?.putAll(sequenceOf(
                Rels.GET_SINGLE_USER to partialUser
        ))

        super._links?.putAll(sequenceOf(
                "self" to Link(Uri.forSingleReportText(id)),
                Rels.GET_MULTIPLE_REPORTS to Link(Uri.forAllReports())
        ))
    }
}
class MultipleReportsResponse(
        reports : List<Item>
): JsonCollection(
        version = "1.0",
        href = Uri.forAllReports(),
        links = mutableListOf(
                CollectionLink("self", Uri.forAllReports()),
                CollectionLink(Rels.CREATE_REPORT, Uri.forAllReports())
        ),
        items = reports
)