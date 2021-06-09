package com.example.skoolworkshop2.dao.payment;

public interface DAOFactory {
    BankDAO getBankDAO();
    PaymentDAO getPaymentDAO();
}
