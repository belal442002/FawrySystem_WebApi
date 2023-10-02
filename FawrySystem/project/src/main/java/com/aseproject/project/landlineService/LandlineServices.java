package com.aseproject.project.landlineService;

import com.aseproject.project.paymentService.PaymentService;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Data
public abstract class LandlineServices {
    public int landlineNumber;
    public double fees;
    public double discount;
    public double firstTransactionDiscount;

    public String AddDiscount(double discount){
        this.discount = discount;
        return "Discount added successfully";
    }

    public abstract boolean check();
    public abstract String calculateFees();
    public abstract String payment(PaymentService paymentService);

    public String payReceipt(int landlineNumber, PaymentService paymentService) {
        this.landlineNumber = landlineNumber;
        if (!check())
            return "You entered wrong number";
        return calculateFees() + "\n" + payment(paymentService);
    }
}
