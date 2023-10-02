package com.aseproject.project.Entity;

import com.aseproject.project.paymentService.PaymentService;
import com.aseproject.project.paymentService.VisaPayment;
import com.aseproject.project.paymentService.WalletPayment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class User {
    private static int counter;
    private int Id;
    private List<PaymentService> paymentServices = new ArrayList<>();
    private boolean firstTransaction;

    public User() {
        this.firstTransaction = true;
        counter++;
        this.Id = counter;
        paymentServices.add(new VisaPayment());
        paymentServices.add(new WalletPayment());
        this.paymentServices.get(0).setAmount(2000);
    }

    public PaymentService getVisa(){
        return this.paymentServices.get(0);
    }

    public PaymentService getWallet(){
        return this.paymentServices.get(1);
    }

}