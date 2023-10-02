package com.aseproject.project.InternetService;

import com.aseproject.project.paymentService.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class WeInternetPaymentServices extends InternetPaymentService{
    @Override
    public boolean check() {
        String str = Integer.toString(this.phoneNumber);
        if(str.length() != 8)
            return false;
        return true;
    }

    @Override
    public String CalculateFees() {
        this.fees = 200;
        if (this.firstTransactionDiscount > 0)
            this.discount += 0.1;
        if(this.discount == 0)
            return "your fees: " + this.fees + " LE";

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

    @Override
    public String payment(PaymentService p) {
        return p.payForService(this.fees);
    }
}
