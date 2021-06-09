package com.example.skoolworkshop2.dao.localDatabase.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.skoolworkshop2.domain.Product;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ShoppingCartItem {
    @ColumnInfo @PrimaryKey @NonNull
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

    public ShoppingCartItem(int id, int productId, boolean workshop, String date, int rounds, int workshopPerWorkshopRound, int roundDuration, String productIds, String timeSchedule, int participants, int amountOfParticipantsGraffitiTshirt, String learningLevel) {
        this.id = id;
        this.productId = productId;
        this.workshop = workshop;
        this.date = date;
        this.rounds = rounds;
        this.workshopPerWorkshopRound = workshopPerWorkshopRound;
        this.roundDuration = roundDuration;
        this.productIds = productIds;
        this.timeSchedule = timeSchedule;
        this.participants = participants;
        this.amountOfParticipantsGraffitiTshirt = amountOfParticipantsGraffitiTshirt;
        this.learningLevel = learningLevel;
    }

//    public ShoppingCartItem(int productId, boolean workshop, String date, int rounds, int workshopPerWorkshopRound, int roundDuration, List<Integer> productIds, String timeSchedule, int participants, int amountOfParticipantsGraffitiTshirt, String learningLevel) {
//        this.productId = productId;
//        this.workshop = workshop;
//        this.date = date;
//        this.rounds = rounds;
//        this.workshopPerWorkshopRound = workshopPerWorkshopRound;
//        this.roundDuration = roundDuration;
//        this.productIds = convertIdsToString(productIds);
//        this.timeSchedule = timeSchedule;
//        this.participants = participants;
//        this.amountOfParticipantsGraffitiTshirt = amountOfParticipantsGraffitiTshirt;
//        this.learningLevel = learningLevel;
//    }

    private String convertIdsToString(List<Integer> ids){
        String idString = "";
        for (Integer integer : ids) {
            idString += ids + ";";
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductIds() {
        return productIds;
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
        return timeSchedule;
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
}
