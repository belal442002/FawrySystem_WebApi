package com.aseproject.project.paymentService;

import org.springframework.stereotype.Service;

@Service
public class WalletPayment extends PaymentService{

    @Override
    public String payForService(double cost) {
        if(this.amount < cost || this.amount == 0)
            return "No enough money";
        else
            this.amount -= cost;
        return this.printAmount();
    }

    @Override
    public String Deposit(double amount) {
        this.amount += amount;
        return "funds added successfully" + "\nWallet: " + this.printAmount();
    }

    @Override
    public double Withdraw(double amount) {
        if(this.amount < amount || this.amount ==0)
            return 0;
        this.amount -= amount;
        return amount;
    }
}
