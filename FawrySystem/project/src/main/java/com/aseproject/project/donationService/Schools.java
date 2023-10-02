package com.aseproject.project.donationService;

import com.aseproject.project.paymentService.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class Schools extends DonationService{
    final double taxes = (double) 10/100;

    @Override
    public String calculateFees() {
        this.fees = this.donation.amount + (this.donation.amount * this.taxes);
        return "Your fees: " + this.fees + " LE";
    }
    @Override
    public String transfer(PaymentService p){
        return p.payForService(this.fees);
    }
}
