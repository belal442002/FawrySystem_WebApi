package com.aseproject.project.Entity;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction {
    public String email;
    public String transName;
    public double amount;
    public double fees;
}
