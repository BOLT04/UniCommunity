package pt.isel.g20.unicommunity.board.model;

import javax.persistence.*;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    public Board(){}

    public Board(String name, String description){
        this.name=name;
        this.description=description;
    }

    public Board(Long id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Board(String name){
        this.name=name;
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
}
