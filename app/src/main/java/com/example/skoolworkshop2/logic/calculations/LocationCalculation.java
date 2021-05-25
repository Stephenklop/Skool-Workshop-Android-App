package com.example.skoolworkshop2.logic.calculations;

import com.example.skoolworkshop2.dao.location.ApiCoordinateRequest;
import com.example.skoolworkshop2.dao.location.CoordinateRequest;

public class LocationCalculation {
    private CoordinateRequest coordinateRequest = new ApiCoordinateRequest();

    public double[] getCoordinates(String postalCode){
        Thread useApi = new Thread(new Runnable() {
            @Override
            public void run() {
                coordinateRequest.getCoordinates(postalCode);
            }
        });
        try{
            useApi.start();
            useApi.join();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
