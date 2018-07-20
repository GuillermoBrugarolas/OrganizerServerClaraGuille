package model;

/**
 * Created by Guillermo Brugarolas on 12/06/2018.
 */
public class Task {
    private String name;
    private int position;
    private String description;
    private Tag tag;
    private User userAssigned;

    public Task() {
    }

    public Task(String name, int position, String description, Tag tag, User userAssigned) {
        this.name = name;
        this.position = position;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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
                ", position=" + position +
                ", description='" + description + '\'' +
                ", tag=" + tag +
                ", userAssigned=" + userAssigned +
                '}';
    }
}
