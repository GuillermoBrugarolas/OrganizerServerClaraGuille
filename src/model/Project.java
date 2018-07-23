package model;


import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Guillermo Brugarolas on 12/06/2018.
 */
public class Project implements Serializable{
    private static final long serialVersionUID = 42L;
    private User owner;
    private LinkedList<User> members;
    private String name;
    private String id;
    private Column columnOne;
    private Column columnTwo;
    private Column columnThree;
    private String background;

    public Project() {
    }

    public Project(String name){
        this.name= name;
    }

    public Project(User owner, LinkedList<User> members, String name, String id, Column c1, Column c2, Column c3, String background) {
        this.owner = owner;
        this.members = members;
        this.name = name;
        this.id = id;
        this.columnOne = c1;
        this.columnOne = c2;
        this.columnOne = c3;
        this.background = background;
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

    public void addMember(User member){
        this.getMembers().add(member);
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

    public Column getColumn(int i) {
        if (i == 1){
            return this.columnOne;
        } else if (i == 2){
            return this.columnTwo;
        } else if (i == 3){
            return this.columnThree;
        }
        return null;
    }

    public void setColumnOne(Column c1) {
        this.columnOne = c1;
    }

    public void setColumnTwo(Column c2) {
        this.columnTwo = c2;
    }

    public void setColumnThree(Column c3) {
        this.columnThree = c3;
    }
    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    @Override
    public String toString() {
        return "Project{" + name +
                ", owner=" + owner.getNickname() +
                ", id='" + id +
                '}';
    }
}
