package com.example.skoolworkshop2;

public class Workshop {
    private int id;
    private String name;
    private String description;
    private double price;
    private int maxParticipants;
    private String category;

    public Workshop(int id, String name, String description, double price, int maxParticipants, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.maxParticipants = maxParticipants;
        this.category = category;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Workshop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", maxParticipants=" + maxParticipants +
                '}';
    }
}
