package com.aseproject.project.Entity;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Refund {
    public String ServiceName;
    public double amount;
    public Account account;

    public String getRefund() {
        return "Service name: " + this.ServiceName + "\n" + "Amount: " + this.amount;
    }
}
