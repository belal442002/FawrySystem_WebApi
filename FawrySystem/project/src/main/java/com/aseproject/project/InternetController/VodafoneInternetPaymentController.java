package com.aseproject.project.InternetController;

import com.aseproject.project.Entity.Account;
import com.aseproject.project.Entity.PaymentTransaction;
import com.aseproject.project.InternetService.VodafoneInternetPaymentServices;
import com.aseproject.project.service.AccountService;
import com.aseproject.project.service.PaymentTransactionService;
import com.aseproject.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VodafoneInternetPaymentController {

    @Autowired
    private VodafoneInternetPaymentServices vodafoneInternetPaymentServices;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentTransactionService paymentTransactionService;

    //Add discount to vodafone internet payment service (http://localhost:8082/vodafoneInternetDiscount/{discount})
    @PostMapping("/vodafoneInternetDiscount/{discount}")
    public String AddDiscount(@PathVariable("discount") double discount){
        return vodafoneInternetPaymentServices.AddDiscount(discount);
    }

    //Get vodafone internet payment service and pay by visa (http://localhost:8082/vodafoneInternet/visa/{phoneNumber}/{cardNumber}/{password})
    @GetMapping("/vodafoneInternet/visa/{phoneNumber}/{cardNumber}/{password}")
    public String Recharge(@RequestBody Account account, @PathVariable("phoneNumber") int phoneNumber, @PathVariable("cardNumber") String cardNumber, @PathVariable("password") int password){
        if(!accountService.checkAccount(account))
            return "Invalid account";
        if(cardNumber.equals("") || password == 0 || Integer.toString(password).length() < 4)
            return "Invalid visa account";

        if (userService.getUserById(accountService.getId()).isFirstTransaction()) {
            vodafoneInternetPaymentServices.setFirstTransactionDiscount(0.1);
            userService.getUserById(accountService.getId()).setFirstTransaction(false);
        }
        double amount = 150;
        double fees = amount + (amount*0.1);
        paymentTransactionService.addPaymentTransaction(new PaymentTransaction(account.getEmail(), "vodafone internet payment service", amount, fees));
        return vodafoneInternetPaymentServices.payInternet(phoneNumber, userService.getUserById(accountService.getId()).getVisa());
    }

    //Get vodafone internet payment service by wallet (http://localhost:8082/vodafoneInternet/wallet/{phoneNumber})
    @GetMapping("/vodafoneInternet/wallet/{phoneNumber}")
    public String Recharge(@RequestBody Account account, @PathVariable("phoneNumber") int phoneNumber){
        if(!accountService.checkAccount(account))
            return "Invalid account";

        if (userService.getUserById(accountService.getId()).isFirstTransaction()) {
            vodafoneInternetPaymentServices.setFirstTransactionDiscount(0.1);
            userService.getUserById(accountService.getId()).setFirstTransaction(false);
        }
        double amount = 150;
        double fees = amount + (amount*0.1);
        paymentTransactionService.addPaymentTransaction(new PaymentTransaction(account.getEmail(), "Vodafone internet payment service", amount, fees));
        return vodafoneInternetPaymentServices.payInternet(phoneNumber, userService.getUserById(accountService.getId()).getWallet());
    }
}
