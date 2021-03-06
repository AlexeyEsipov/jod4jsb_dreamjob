package ru.job4j.dreamjob.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Post implements Serializable {
    private int id;
    private String name;
    private String description;
    private boolean visible;
    private City city;
    private final LocalDateTime created =
            LocalDateTime.now().truncatedTo(TimeUnit.MINUTES.toChronoUnit());

    public Post(int id, String name, City city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public Post(int id, String name, String description, City city, boolean visible) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.visible = visible;
    }

    private Post() {
    }

    public Post(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
