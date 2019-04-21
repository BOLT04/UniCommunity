package pt.isel.g20.unicommunity.forumItem.model;

import javax.persistence.*;

@Entity
public class ForumItem {

    private long boardId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    public ForumItem(){}

    public ForumItem(Long boardId, String name, String content){
        this.boardId = boardId;
        this.name=name;
        this.content = content;
    }

    public ForumItem(Long boardId, Long id, String name, String content){
        this.boardId = boardId;
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }
}

