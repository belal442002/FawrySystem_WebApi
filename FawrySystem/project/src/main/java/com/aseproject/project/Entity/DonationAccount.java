package com.aseproject.project.Entity;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DonationAccount {
    private Donation donation;
    private Account account;
}
