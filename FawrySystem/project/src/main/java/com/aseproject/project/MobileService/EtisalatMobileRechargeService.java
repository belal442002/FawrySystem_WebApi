package com.aseproject.project.MobileService;

import com.aseproject.project.paymentService.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class EtisalatMobileRechargeService extends MobileRechargeService{

    private final String code = "011";
    private final double taxes = (double) 45/100;

    @Override
    public boolean check() {
        String str = "";
        str += this.mobile.phoneNumber.charAt(0);
        str += this.mobile.phoneNumber.charAt(1);
        str += this.mobile.phoneNumber.charAt(2);

        if(!str.equals(this.code) && this.mobile.phoneNumber.length() != 11){
            return false;
        }
        return true;
    }

    @Override
    public String CalculateFees() {
        String str = null;
        if(this.discount == 0){
            this.fees = mobile.amount + (mobile.amount * this.taxes);
            str = "Your fees: " + this.fees + " LE";
        }
        else if(this.discount > 0){
            this.fees = this.fees - (this.fees * this.discount);
            str = "Your fees after applying the discount: " + this.fees + " LE";
        }
        return str;
    }

    @Override
    public String payment(PaymentService p) {
        return p.payForService(this.fees);
    }
}
