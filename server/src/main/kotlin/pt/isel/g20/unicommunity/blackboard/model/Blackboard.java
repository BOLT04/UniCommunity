package pt.isel.g20.unicommunity.blackboard.model;


import javax.persistence.*;

@Entity
public class Blackboard {

    private long boardId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String notificationLevel;

    public Blackboard(){}

    public Blackboard(Long boardId, String name, String description, String notificationLevel){
        this.boardId = boardId;
        this.name=name;
        this.description=description;
        this.notificationLevel = notificationLevel;
    }

    public Blackboard(Long boardId, Long id, String name, String description, String notificationLevel){
        this.boardId = boardId;
        this.id = id;
        this.name = name;
        this.description = description;
        this.notificationLevel = notificationLevel;
    }

    public Blackboard(Long boardId, String name, String notificationLevel){
        this.boardId = boardId;
        this.name=name;
        this.notificationLevel = notificationLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getNotificationLevel() {
        return notificationLevel;
    }

    public void setNotificationLevel(String notificationLevel) {
        this.notificationLevel = notificationLevel;
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }
}

