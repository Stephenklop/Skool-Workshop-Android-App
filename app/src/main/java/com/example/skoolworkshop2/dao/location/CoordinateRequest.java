package com.example.skoolworkshop2.dao.location;

public interface CoordinateRequest {
    double[] getCoordinates(String postalCode);
    double getDistance (double[] coordinatesPostalCode);
}
