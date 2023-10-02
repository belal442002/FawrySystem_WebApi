package com.aseproject.project.donationController;

import com.aseproject.project.Entity.DonationAccount;
import com.aseproject.project.Entity.PaymentTransaction;
import com.aseproject.project.donationService.NGOs;
import com.aseproject.project.service.AccountService;
import com.aseproject.project.service.PaymentTransactionService;
import com.aseproject.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NGOsController {
    @Autowired
    private NGOs ngOs;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentTransactionService paymentTransactionService;

    //Get request to donate to NGOs and pay by visa (http://localhost:8082/NGOsDonation/visa/{cardNumber}/{password})
    @PostMapping("/NGOsDonation/visa/{cardNumber}/{password}")
    public String Recharge(@RequestBody DonationAccount donationAccount, @PathVariable("cardNumber") String cardNumber, @PathVariable("password") int password){
        if(!accountService.checkAccount(donationAccount.getAccount()))
            return "Invalid account";
        if(cardNumber.equals("") || password == 0 || Integer.toString(password).length() < 4)
            return "Invalid visa account";

        double amount = donationAccount.getDonation().getAmount();
        double fees = amount + (amount*0.1);
        if (userService.getUserById(accountService.getId()).isFirstTransaction()) {
            donationAccount.getDonation().setAmount(donationAccount.getDonation().getAmount() * 0.1);
            userService.getUserById(accountService.getId()).setFirstTransaction(false);
        }
        paymentTransactionService.addPaymentTransaction(new PaymentTransaction(donationAccount.getAccount().getEmail(), "NGOs Donation", amount, fees));
        return ngOs.donate(donationAccount.getDonation(), userService.getUserById(accountService.getId()).getVisa());
    }


    //Get request to donate to NGOs and pay by wallet (http://localhost:8082/NGOsDonation/wallet)
    @PostMapping("/NGOsDonation/wallet")
    public String Recharge(@RequestBody DonationAccount donationAccount){
        if(!accountService.checkAccount(donationAccount.getAccount()))
            return "Invalid account";

        double amount = donationAccount.getDonation().getAmount();
        double fees = amount + (amount*0.1);
        if (userService.getUserById(accountService.getId()).isFirstTransaction()) {
            donationAccount.getDonation().setAmount(donationAccount.getDonation().getAmount() * 0.1);
            userService.getUserById(accountService.getId()).setFirstTransaction(false);
        }
        paymentTransactionService.addPaymentTransaction(new PaymentTransaction(donationAccount.getAccount().getEmail(), "NGOs Donation", amount, fees));
        return ngOs.donate(donationAccount.getDonation(), userService.getUserById(accountService.getId()).getWallet());
    }
}
