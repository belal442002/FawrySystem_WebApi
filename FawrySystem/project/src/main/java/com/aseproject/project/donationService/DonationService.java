package com.aseproject.project.donationService;

import com.aseproject.project.Entity.Donation;
import com.aseproject.project.paymentService.PaymentService;

public abstract class DonationService {
    public Donation donation;
    public double fees;


    public abstract String calculateFees();
    public abstract String transfer(PaymentService paymentService);

    public String donate(Donation donation, PaymentService p) {
        this.donation = donation;
        return calculateFees() + "\n" + transfer(p);
    }
}

