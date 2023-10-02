package com.aseproject.project.service;

import com.aseproject.project.Entity.PaymentTransaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentTransactionService {
    private List<PaymentTransaction> list = new ArrayList<>();

    public void addPaymentTransaction(PaymentTransaction paymentTransaction){
        this.list.add(paymentTransaction);
    }

    public List<PaymentTransaction> getAllPaymentTransaction(){
        return this.list;
    }
}
