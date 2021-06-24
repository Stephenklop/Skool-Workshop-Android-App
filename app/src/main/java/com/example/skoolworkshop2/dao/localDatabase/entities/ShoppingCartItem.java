package com.example.skoolworkshop2.dao.localDatabase.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ShoppingCartItem {
    @ColumnInfo @PrimaryKey(autoGenerate = true) @NonNull
    private int id;

    @ColumnInfo
    private int productId;

    @ColumnInfo
    private boolean workshop;

    @ColumnInfo
    private String date;

    @ColumnInfo
    private int rounds;

    @ColumnInfo
    private int workshopPerWorkshopRound;

    @ColumnInfo
    private int roundDuration;

    @ColumnInfo
    private String productIds;

    @ColumnInfo
    private String timeSchedule;

    @ColumnInfo
    private int participants;

    @ColumnInfo
    private int amountOfParticipantsGraffitiTshirt;

    @ColumnInfo
    private String learningLevel;

    @ColumnInfo
    private String startDate;

    @ColumnInfo
    private String endDate;

    @ColumnInfo
    private double regularPrice;

    @ColumnInfo
    private double price;

    public ShoppingCartItem() {}

    public ShoppingCartItem(int productId, boolean workshop, String date, int rounds, int workshopPerWorkshopRound, int roundDuration, String timeSchedule, int participants, int amountOfParticipantsGraffitiTshirt, String learningLevel, String startDate, String endDate, double regularPrice, double price) {
        this.productId = productId;
        this.workshop = workshop;
        this.date = date;
        this.rounds = rounds;
        this.workshopPerWorkshopRound = workshopPerWorkshopRound;
        this.roundDuration = roundDuration;
        this.timeSchedule = timeSchedule;
        this.participants = participants;
        this.amountOfParticipantsGraffitiTshirt = amountOfParticipantsGraffitiTshirt;
        this.learningLevel = learningLevel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.regularPrice = regularPrice;
        this.price = price;
    }

    private String convertIdsToString(List<Integer> ids){
        String idString = "";
        for (Integer integer : ids) {
            idString += integer + ";";
        }
        return idString;
    }

    private List<Integer> convertStringToIds(String ids){
        String[] idSplit = ids.split(";");
        List<Integer> returnIds = new ArrayList<>();
        for (String id : idSplit){
            returnIds.add(Integer.parseInt(id));
        }
        return returnIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductIds() {
        return productIds;
    }

    public List<Integer> getProductIdsList() {
        List<Integer> result = new ArrayList<>();
        String[] ids = productIds.split(";");

        for (int i = 0; i < ids.length; i++) {
            String item = ids[i];

            result.add(Integer.parseInt(item));
        }

        return result;
    }

    public void addProductId(int productId) {
        productIds = getProductIds() + ", " + productId;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public int getProduct() {
        return productId;
    }

    public void setProduct(int productId) {
        this.productId = productId;
    }

    public boolean isWorkshop() {
        return workshop;
    }

    public void setWorkshop(boolean workshop) {
        this.workshop = workshop;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getWorkshopPerWorkshopRound() {
        return workshopPerWorkshopRound;
    }

    public void setWorkshopPerWorkshopRound(int workshopPerWorkshopRound) {
        this.workshopPerWorkshopRound = workshopPerWorkshopRound;
    }

    public int getRoundDuration() {
        return roundDuration;
    }

    public void setRoundDuration(int roundDuration) {
        this.roundDuration = roundDuration;
    }

    public List<Integer> getProducts() {
        return convertStringToIds(productIds);
    }

    public void setProducts(List<Integer> productIds) {
        this.productIds = convertIdsToString(productIds);
    }

    public String getTimeSchedule() {
        // Replace the \n with a space to avoid api errors
        return timeSchedule.replace("\n", " ");
    }

    public void setTimeSchedule(String timeSchedule) {
        this.timeSchedule = timeSchedule;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getAmountOfParticipantsGraffitiTshirt() {
        return amountOfParticipantsGraffitiTshirt;
    }

    public void setAmountOfParticipantsGraffitiTshirt(int amountOfParticipantsGraffitiTshirt) {
        this.amountOfParticipantsGraffitiTshirt = amountOfParticipantsGraffitiTshirt;
    }

    public String getLearningLevel() {
        return learningLevel;
    }

    public void setLearningLevel(String learningLevel) {
        this.learningLevel = learningLevel;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setRegularPrice(double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public double getRegularPrice() {
        return regularPrice;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ShoppingCartItem{" +
                "id=" + id +
                ", productId=" + productId +
                ", workshop=" + workshop +
                ", date='" + date + '\'' +
                ", rounds=" + rounds +
                ", workshopPerWorkshopRound=" + workshopPerWorkshopRound +
                ", roundDuration=" + roundDuration +
                ", productIds='" + productIds + '\'' +
                ", timeSchedule='" + timeSchedule + '\'' +
                ", participants=" + participants +
                ", amountOfParticipantsGraffitiTshirt=" + amountOfParticipantsGraffitiTshirt +
                ", learningLevel='" + learningLevel + '\'' +
                ", totalPrice=" + price +
                '}';
    }
}
