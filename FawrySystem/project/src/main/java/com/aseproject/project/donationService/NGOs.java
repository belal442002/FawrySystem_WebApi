package com.aseproject.project.donationService;

import com.aseproject.project.paymentService.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class NGOs extends DonationService{
    @Override
    public String calculateFees() {
        this.fees = this.donation.amount;
        return "Your fees: " + this.fees + " LE";
    }
    @Override
    public String transfer(PaymentService p){
        return p.payForService(this.fees);
    }
}
