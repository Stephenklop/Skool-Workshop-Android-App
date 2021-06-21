package com.example.skoolworkshop2.logic.calculations;

import com.example.skoolworkshop2.dao.location.ApiCoordinateRequest;
import com.example.skoolworkshop2.dao.location.CoordinateRequest;

public class LocationCalculation {
    private CoordinateRequest coordinateRequest = new ApiCoordinateRequest();

    public double getDistance(String postalCode, String country){
        final Double[] distance = {null};
        final double[][] coordinates = new double[1][1];

        Thread getCoordinates = new Thread(() -> {
            coordinates[0] = coordinateRequest.getCoordinates(postalCode, country);
        });

        Thread getDistance = new Thread(() -> {
            distance[0] = coordinateRequest.getDistance(coordinates[0]);
        });

        try {
            getCoordinates.start();
            getCoordinates.join();
            getDistance.start();
            getDistance.join();
        } catch (Exception e){
            e.printStackTrace();
        }
        return distance[0];
    }
}
