package pt.isel.g20.unicommunity.features.report.model

import pt.isel.g20.unicommunity.common.*

fun Report.toItemRepr(): Item {
    val comment = this.comment
    val forumItem = this.forumItem
    val links = mutableListOf(
            CollectionLink(
                    rel = "self",
                    href = Uri.forSingleReportText(this.id)
            ),
            CollectionLink(
                    rel = Rels.GET_SINGLE_USER,
                    href = Uri.forSingleUserText(this.user.id)
            ),
            CollectionLink(
                    rel = Rels.GET_MULTIPLE_REPORTS,
                    href = Uri.forAllReports()
            )
    )

    if(comment != null) {
        links.addAll(sequenceOf(
                CollectionLink(
                        rel = Rels.GET_SINGLE_COMMENT,
                        href = Uri.forSingleCommentText(
                                comment.forumItem.forum.id,
                                comment.forumItem.id,
                                comment.id
                        )
                )
        ))
    }
    else if(forumItem != null){
        links.addAll(sequenceOf(
                CollectionLink(
                        rel = Rels.GET_SINGLE_FORUMITEM,
                        href = Uri.forSingleForumItemText(forumItem.forum.id, forumItem.id)
                )
        ))
    }

    return Item(
            href = Uri.forSingleReportText(this.id),
            data = listOf(
                    Data(name = "id", value = this.id.toString()),
                    Data(name = "userId", value = this.user.id.toString()),
                    Data(name = "userName", value = this.user.name),
                    Data(name = "forumItemId", value = forumItem?.id?.toString()
                            ?: "null"),
                    Data(name = "commentId", value = comment?.id?.toString() ?: "null"),
                    Data(name = "numberOfReports", value = this.numberOfReports.toString())
            ),
            links = links
    )
}