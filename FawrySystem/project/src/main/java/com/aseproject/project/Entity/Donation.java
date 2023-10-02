package com.aseproject.project.Entity;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Donation {
    public int serviceCode;
    public double amount;
}
