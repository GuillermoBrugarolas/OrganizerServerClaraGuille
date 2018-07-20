package model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Guillermo Brugarolas on 12/06/2018.
 */
public class Column implements Serializable{
    private static final long serialVersionUID = 42L;
    private String name;
    private int position;
    private LinkedList<Task> tasks;

    public Column() {
    }

    public Column(String name, int position, LinkedList<Task> tasks) {
        this.name = name;
        this.position = position;
        this.tasks = tasks;
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

    public LinkedList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(LinkedList<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", position=" + position +
                ", tasks=" + tasks +
                '}';
    }
}
