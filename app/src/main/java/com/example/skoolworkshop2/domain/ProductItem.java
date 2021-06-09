package com.example.skoolworkshop2.domain;

public interface ProductItem {
    Product getProduct();
    String getDate();
    void setDate(String date);
    int getParticipants();
    void setParticipants(int participants);
    int getRounds();
    void setRounds(int rounds);
    int getRoundDuration();
    void setRoundDuration(int roundDuration);
    String getTimeSchedule();
    void setTimeSchedule(String timeSchedule);
    String getLearningLevel();
    void setLearningLevel(String learningLevel);
    double getPrice();
}
