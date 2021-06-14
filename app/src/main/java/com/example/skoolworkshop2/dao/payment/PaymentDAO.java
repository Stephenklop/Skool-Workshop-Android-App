package com.example.skoolworkshop2.dao.payment;

import com.example.skoolworkshop2.domain.Bank;
import com.example.skoolworkshop2.domain.Payment;

public interface PaymentDAO {
    Payment addPayment(int orderId, String amount, String description, Bank bank);
}
