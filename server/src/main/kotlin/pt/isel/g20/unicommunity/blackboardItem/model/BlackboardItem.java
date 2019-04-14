package pt.isel.g20.unicommunity.blackboardItem.model;

import javax.persistence.*;

@Entity
public class BlackboardItem {

    @Column
    private long boardId;

    @Column
    private long bbId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String content;

    public BlackboardItem(){}

    public BlackboardItem(long boardId, long bbId, String name, String content){
        this.boardId = boardId;
        this.bbId = bbId;
        this.name = name;
        this.content = content;
    }

    public BlackboardItem(long boardId, long bbId, long id, String name, String content){
        this.boardId = boardId;
        this.bbId = bbId;
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public long getBbId() {
        return bbId;
    }

    public void setBbId(int bbId) {
        this.bbId = bbId;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
