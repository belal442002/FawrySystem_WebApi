package com.aseproject.project.Entity;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    public String email;
    public double deposit;
    public String wallet;
}
