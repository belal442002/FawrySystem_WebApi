package com.aseproject.project.landlineService;

import com.aseproject.project.paymentService.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class QuarterReceipt extends LandlineServices{

    public boolean check() {
        String str = Integer.toString(this.landlineNumber);
        if(str.length() != 8)
            return false;

        return true;
    }
    public String calculateFees() {
        this.fees = 300;
        if (this.firstTransactionDiscount > 0)
            this.discount += 0.1;

        if (this.discount == 0)
            return "Your Quarter receipt fees: " + this.fees+ " LE";

        if(this.discount > 0){
            this.fees = (this.fees - (this.fees * this.discount));
            if(this.firstTransactionDiscount > 0){
                this.discount -= 0.1;
                this.firstTransactionDiscount = 0;
            }
            return "your fees after applying discount: " + fees + " LE";
        }
        return null;
    }

    public String payment(PaymentService p){
        return p.payForService(this.fees);
    }

}
