package pt.isel.g20.unicommunity.forum.model;

import javax.persistence.*;

@Entity
public class Forum {

    @Id
    private long boardId;

    private boolean allowImagePosts;

    public Forum(){}

    public Forum(Long boardId){
        this(boardId, false);
    }

    public Forum(Long boardId, boolean allowImagePosts){
        this.boardId = boardId;
        this.allowImagePosts = allowImagePosts;
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public boolean isAllowImagePosts() {
        return allowImagePosts;
    }

    public void setAllowImagePosts(boolean allowImagePosts) {
        this.allowImagePosts = allowImagePosts;
    }
}
