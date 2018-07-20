package model;

import java.awt.*;

/**
 * Created by Guillermo Brugarolas on 12/06/2018.
 */
public class Tag {

    private String name;
    private Color color;

    public Tag() {
    }

    public Tag(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", color=" + color +
                '}';
    }
}