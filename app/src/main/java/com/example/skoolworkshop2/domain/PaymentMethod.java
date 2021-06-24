package com.example.skoolworkshop2.domain;

import java.util.Arrays;
import java.util.List;

public enum PaymentMethod {
    BACS("bacs", "Directe bankoverschrijving"),
    CHECQUE("cheque", "Betalen via CJP of contant"),
    IDEAL("mollie_wc_gateway_ideal", "iDEAL");

    private List<String> fields;
    PaymentMethod(String... fields){
        this.fields = Arrays.asList(fields);
    }
    public List<String> getFields(){
        return fields;
    }
}
