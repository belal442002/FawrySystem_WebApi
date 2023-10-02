package com.aseproject.project.InternetService;

import com.aseproject.project.paymentService.PaymentService;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@NoArgsConstructor
public abstract class InternetPaymentService {
    public int phoneNumber;
    public double discount;
    public double fees;
    public double firstTransactionDiscount;

    public String AddDiscount(double discount){
        this.discount = discount;
        return "Discount added successfully";
    }
    public abstract boolean check();
    public abstract String CalculateFees();
    public abstract String payment(PaymentService p);

    public String payInternet(int phoneNumber, PaymentService paymentService){
        this.phoneNumber = phoneNumber;
            if(!check())
                return "You Entered wrong phone number";
            return CalculateFees() + "\n" + payment(paymentService);
    }
}
