package isel.pt.unicommunity.model.collectionjson

import isel.pt.unicommunity.model.CollectionJson
import isel.pt.unicommunity.model.links.BlackBoardSettingsLink

fun CollectionJson.toBlackBoardSettingsCollection() : BlackBoardSettingsCollection {

    with(this.collection){
        return BlackBoardSettingsCollection(
            BlackBoardSettingsLink(href),
            items.map {
                with(DataExtractor(it.data, it.links)) {
                    BlackBoardSettingsCollection.PartialBlackBoardSettings(
                        getValue("userId"),
                        getValue("bbId"),
                        getValue("notificationLevel"),
                        getValue("bbFcmTopicName")
                    )
                }
            }
        )
    }
}


class BlackBoardSettingsCollection(
    val self: BlackBoardSettingsLink,
    val blackBoardseSttings : List<PartialBlackBoardSettings>
){
    class PartialBlackBoardSettings(
        val userId: String,
        val bbId: String,
        val notificationLevel: String,
        val bbFcmTopicName: String
    )
}
