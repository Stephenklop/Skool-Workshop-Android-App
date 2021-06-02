package com.example.skoolworkshop2.logic.calculations;

import com.example.skoolworkshop2.dao.location.ApiCoordinateRequest;
import com.example.skoolworkshop2.dao.location.CoordinateRequest;

public class LocationCalculation {
    private CoordinateRequest coordinateRequest = new ApiCoordinateRequest();

    public double getDistance(String postalCode){
        final Double[] distance = {null};
        final double[][] coordinates = new double[1][1];
        Thread getCoordinates = new Thread(new Runnable() {
            @Override
            public void run() {
                coordinates[0] = coordinateRequest.getCoordinates(postalCode);
                System.out.println(coordinates[0]);
            }
        });
        Thread getDistance = new Thread(new Runnable() {
            @Override
            public void run() {
                distance[0] = coordinateRequest.getDistance(coordinates[0]);
                System.out.println(distance[0]);
            }
        });
        try{
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
