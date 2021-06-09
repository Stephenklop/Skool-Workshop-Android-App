package com.example.skoolworkshop2.dao.payment;

public class MollieDAOFactory implements DAOFactory {

    @Override
    public BankDAO getBankDAO() {
        return new MollieBankDAO();
    }

    @Override
    public PaymentDAO getPaymentDAO() {
        return new MolliePaymentDAO();
    }
}