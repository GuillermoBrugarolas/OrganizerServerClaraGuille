package model;

/**
 * Created by Guillermo Brugarolas on 19/07/2018.
 */
public class TasksObject extends Object {
    private String username;
    private int position;
    private int pending;
    private int assigned;
    private double percentage;

    public TasksObject() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public TasksObject(String username, int pending, int assigned, double percentage) {
        this.username = username;
        this.pending = pending;
        this.assigned = assigned;
        this.position = 0;
        this.percentage = percentage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    public int getAssigned() {
        return assigned;
    }

    public void setAssigned(int assigned) {
        this.assigned = assigned;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
