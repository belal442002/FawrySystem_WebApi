package com.aseproject.project.MobileService;

import com.aseproject.project.Entity.Mobile;
import com.aseproject.project.paymentService.PaymentService;
import lombok.*;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class MobileRechargeService {
    public double fees;
    public double discount;
    public Mobile mobile;

    public String AddDiscount(double discount){
        this.discount = discount;
        return "Discount added successfully";
    }
    public abstract boolean check();
    public abstract String CalculateFees();
    public abstract String payment(PaymentService p);

    public String Recharge(Mobile mobile, PaymentService paymentService) {
        this.mobile = mobile;
        if (!check())
            return  "You entered wrong phone number";

        return CalculateFees() + "\n" + payment(paymentService);
    }
}
