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
    private String project;
    private String category;

    public Task() {
    }

    public Task(String name, int column, String description, Tag tag, User userAssigned, String project, String category) {
        this.name = name;
        this.column = column;
        this.description = description;
        this.tag = tag;
        this.userAssigned = userAssigned;
        this.project = project;
        this.category = category;
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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Task{" + name +
                ", tag=" + tag.getName() +
                ", userAssigned=" + userAssigned.getNickname() +
                ", project=" + project +
                ", category=" + category +
                '}';
    }
}