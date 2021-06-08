package com.example.skoolworkshop2.domain;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private double id;
    private String name;
    private String permalink;
    private String shortDescription;
    private String description;
    private String sourceImage;
    private String thumbnailImage;
    private String imageName;
    private int rounds;
    private int roundDuration;
    private int participants;
    private String timeSchedule;
    private String learningLevel;

    public Product(double id, String name, String permalink, String shortDescription, String description, String sourceImage, String imageName) {
        this.id = id;
        this.name = name;
        this.permalink = permalink;
        this.shortDescription = shortDescription;
        this.description = description;
        this.sourceImage = sourceImage;
        this.thumbnailImage = sourceImage.replace(".jpg", "-300x300.jpg");
        this.imageName = imageName;
        this.rounds = 1;
        this.roundDuration = 60;
        this.participants = 0;
    }

    public double getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getSourceImage() {
        return sourceImage;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public String getImageName() {
        return imageName;
    }

    public int getRounds() {
        return rounds;
    }

    public int getRoundDuration() {
        return roundDuration;
    }

    public int getParticipants() {
        return participants;
    }

    public int getTotalDuration() {
        return rounds * roundDuration;
    }

    public String getTimeSchedule() {
        return timeSchedule;
    }

    public String getLearningLevel() {
        return learningLevel;
    }

    public double getPrice() {
        double result;

        if (name.toLowerCase().contains("graffiti") || name.toLowerCase().contains("t-shirt")) {
            result = (participants * 7.50) + ((roundDuration * rounds) * 2.50);
        } else {
            result = (roundDuration * rounds) * 2.50;
        }

        return result;
    }

    public void setId(double id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public void setRoundDuration(int duration) {
        this.roundDuration = duration;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public void setSourceImage(String sourceImage) {
        this.sourceImage = sourceImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setTimeSchedule(String timeSchedule) {
        this.timeSchedule = timeSchedule;
    }

    public void setLearningLevel(String learningLevel) {
        this.learningLevel = learningLevel;
    }

    @Override
    public String toString() {
        return name;
    }
}
