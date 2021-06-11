package com.example.skoolworkshop2.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NonNls;

import java.io.Serializable;

@Entity
public class Product implements Serializable {
    @ColumnInfo @PrimaryKey @NonNull
    private int id;

    @ColumnInfo @NonNull
    private String name;

    @ColumnInfo
    private String productType;

    @ColumnInfo
    private String category;

    @ColumnInfo
    private String permalink;

    @ColumnInfo
    private String type;

    @ColumnInfo
    private String status;

    @ColumnInfo
    private String description;

    @ColumnInfo
    private String shortDescription;

    @ColumnInfo
    private String buildupDescription;

    @ColumnInfo
    private String practicalInformation;

    @ColumnInfo
    private String costsInfo;

    @ColumnInfo
    private String sourceImage;

    @ColumnInfo
    private String imageName;

    @ColumnInfo
    private String video;

    public Product(int id, String name, String productType, String category, String permalink, String type, String status, String description, String shortDescription, String buildupDescription, String practicalInformation, String costsInfo, String sourceImage, String imageName, String video) {
        this.id = id;
        this.name = name;
        this.productType = productType;
        this.category = category;
        this.permalink = permalink;
        this.type = type;
        this.status = status;
        this.description = description;
        this.shortDescription = shortDescription;
        this.buildupDescription = buildupDescription;
        this.practicalInformation = practicalInformation;
        this.costsInfo = costsInfo;
        this.sourceImage = sourceImage;
        this.imageName = imageName;
        this.video = video;
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

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getBuildupDescription() {
        return buildupDescription;
    }

    public void setBuildupDescription(String buildupDescription) {
        this.buildupDescription = buildupDescription;
    }

    public String getPracticalInformation() {
        return practicalInformation;
    }

    public void setPracticalInformation(String practicalInformation) {
        this.practicalInformation = practicalInformation;
    }

    public String getCostsInfo() {
        return costsInfo;
    }

    public void setCostsInfo(String costsInfo) {
        this.costsInfo = costsInfo;
    }

    public String getSourceImage() {
        return sourceImage;
    }

    public void setSourceImage(String sourceImage) {
        this.sourceImage = sourceImage;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
