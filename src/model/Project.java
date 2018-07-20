package model;


import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Guillermo Brugarolas on 12/06/2018.
 */
public class Project implements Serializable {
    private User owner;
    private LinkedList<User> members;
    private String name;
    private String id;
    private LinkedList<Column> columns;
    private String background;
    private LinkedList<Tag> tags;

    public Project() {
    }

    public Project(String name){
        this.name= name;
    }

    public Project(User owner, LinkedList<User> members, String name, String id, LinkedList<Column> categories, String background, LinkedList<Tag> tags) {
        this.owner = owner;
        this.members = members;
        this.name = name;
        this.id = id;
        this.columns = categories;
        this.background = background;
        this.tags = tags;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public LinkedList<User> getMembers() {
        return members;
    }

    public void setMembers(LinkedList<User> members) {
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<Column> getColumns() {
        return columns;
    }

    public void setColumns(LinkedList<Column> columns) {
        this.columns = columns;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public LinkedList<Tag> getTags() {
        return tags;
    }

    public void setTags(LinkedList<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(String name, String color){
        Color c = Color.WHITE;
        if (color.equals("red")){   c = Color.RED;}
        else if (color.equals("blue")){ c = Color.BLUE;}
        else if (color.equals("green")){ c = Color.GREEN;}
        else if (color.equals("yellow")){ c = Color.YELLOW;}
        else if (color.equals("purple")){ c = Color.MAGENTA;}
        else if (color.equals("black")){ c = Color.BLACK;}
        else if (color.equals("orange")){ c = Color.ORANGE;}
        this.getTags().add(new Tag(name, c));
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name +
                ", owner=" + owner +
                ", members=" + members +
                ", id='" + id +
                ", columns=" + columns +
                ", background=" + background +
                ", tags=" + tags +
                '}';
    }
}
