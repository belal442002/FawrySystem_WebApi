package com.aseproject.project.paymentService;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class VisaPayment extends PaymentService{

    private String cardNum;
    private int password;
    @Override
    public String payForService(double cost) {

        if (cost <= this.amount)
            this.amount -= cost;
        else
            return "No enough money";
        return this.printAmount();
    }

    @Override
    public String Deposit(double amount) {
        this.amount += amount;
        return this.printAmount();
    }

    @Override
    public double Withdraw(double amount){
        if(this.amount == 0 || amount > this.amount)
            return 0;
        else {
            this.amount -= amount;
            return amount;
        }

    }
}
