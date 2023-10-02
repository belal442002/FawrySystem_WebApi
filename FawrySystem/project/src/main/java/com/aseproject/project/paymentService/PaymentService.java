package com.aseproject.project.paymentService;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class PaymentService {
    public double amount;
    public abstract String payForService (double cost);
    public abstract String Deposit(double amount);
    public abstract double Withdraw(double amount);
    public String printAmount(){
        return "The amount in Your account: " + this.amount + " LE";
    }
}
