package com.example.outtakeapp.Model;

import java.io.Serializable;
import java.util.List;

public class ItemModel implements Serializable {
    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String address;
    private String description;
    private String distance;
    private String duration;
    private List<String> pic;
    private int price;
    private double score;
    private String TourCount;

    public ItemModel() {}

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistance() {
        return distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<String> getPic() {
        return pic;
    }
    public void setPic(List<String> pic) {
        this.pic = pic;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }

    public String getTimeTour() {
        return TourCount;
    }
    public void setTimeTour(String timeTour) {
        this.TourCount = timeTour;
    }

}

