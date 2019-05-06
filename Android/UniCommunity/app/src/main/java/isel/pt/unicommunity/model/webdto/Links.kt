package isel.pt.unicommunity.model.webdto

class NavigationLink( href:String ): NavLink("/rels/nav", href)
class BoardLink( href:String ): NavLink("/rels/board", href)
class BlackBoardsLink( href:String ): NavLink("/rels/blackboards", href)
class ForumLink( href:String ): NavLink("/rels/forum", href)
class FeedLink( href:String ): NavLink("/rels/feed", href)
class SelfLink( href:String ): NavLink("self", href)
class UserProfileLink( href:String ): NavLink("/rels/userProfile", href)
class HomeLink( href:String ) : NavLink("/rels/home", href)
class LogoutLink( href:String ) : NavLink("/rels/logout", href)
class AllBoardsLink( href:String ) : NavLink("/rels/allBoards", href)
class MyBoardsLink( href:String ) : NavLink("/rels/myBoards", href)