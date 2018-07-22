package model;

import java.io.Serializable;

/**
 * Created by Guillermo Brugarolas on 12/06/2018.
 */
public class Task implements Serializable{
    private static final long serialVersionUID = 42L;
    private String name;
    private int column;
    private String description;
    private Tag tag;
    private User userAssigned;

    public Task() {
    }

    public Task(String name, int column, String description, Tag tag, User userAssigned) {
        this.name = name;
        this.column = column;
        this.description = description;
        this.tag = tag;
        this.userAssigned = userAssigned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int position) {
        this.column = column;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public User getUserAssigned() {
        return userAssigned;
    }

    public void setUserAssigned(User userAssigned) {
        this.userAssigned = userAssigned;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", column=" + column +
                ", description='" + description + '\'' +
                ", tag=" + tag.getName() +
                ", userAssigned=" + userAssigned.getNickname() +
                '}';
    }
}