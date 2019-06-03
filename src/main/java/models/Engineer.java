package models;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class Post {
    private String content;
    private static ArrayList<Post> instances = new ArrayList<>();
    private boolean published;
    private LocalDateTime createdAt; //see constructor and my method
    private int id;

    public Post(String content) {
        this.content = content;
        this.published = false;
        this.createdAt = LocalDateTime.now();
        instances.add(this);
        this.id = instances.size();
    }

    public static TimeZone findBId(int id) {
        return null;
    }


    public String getContent() {
        return content;
    }

    public static ArrayList<Post> getAll() {
        return instances;
    }

    public static void clearAllPosts() {
        instances.clear(); //clear as a method is part of the ArrayList
    }

    public boolean getPublished() {
        return this.published;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getId() {
        return id;
    }

    public static Post findById(int id) {
        return instances.get(id-1); //minus 1 because indexs start at 0
    }

    public void update(String content) {
        this.content = content;
    }
    public void deletePost(){
        instances.remove(id-1); //same reason
    }
}

