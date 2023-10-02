package com.aseproject.project.InternetController;

import com.aseproject.project.Entity.Account;
import com.aseproject.project.Entity.PaymentTransaction;
import com.aseproject.project.InternetService.WeInternetPaymentServices;
import com.aseproject.project.service.AccountService;
import com.aseproject.project.service.PaymentTransactionService;
import com.aseproject.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeInternetPaymentController {

    @Autowired
    private WeInternetPaymentServices weInternetPaymentServices;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentTransactionService paymentTransactionService;

    //Add discount to we internet payment service (http://localhost:8082/weInternetDiscount/{discount})
    @PostMapping("/weInternetDiscount/{discount}")
    public String AddDiscount(@PathVariable("discount") double discount){
        return weInternetPaymentServices.AddDiscount(discount);
    }


    //Get we internet payment service and pay by visa (http://localhost:8082/weInternet/visa/{phoneNumber}/{cardNumber}/{password})
    @GetMapping("/weInternet/visa/{phoneNumber}/{cardNumber}/{password}")
    public String Recharge(@RequestBody Account account, @PathVariable("phoneNumber") int phoneNumber, @PathVariable("cardNumber") String cardNumber, @PathVariable("password") int password){
        if(!accountService.checkAccount(account))
            return "Invalid account";
        if(cardNumber.equals("") || password == 0 || Integer.toString(password).length() < 4)
            return "Invalid visa account";

        if (userService.getUserById(accountService.getId()).isFirstTransaction()) {
            weInternetPaymentServices.setFirstTransactionDiscount(0.1);
            userService.getUserById(accountService.getId()).setFirstTransaction(false);
        }
        double amount = 150;
        double fees = amount + (amount*0.1);
        paymentTransactionService.addPaymentTransaction(new PaymentTransaction(account.getEmail(), "We internet payment service", amount, fees));
        return weInternetPaymentServices.payInternet(phoneNumber, userService.getUserById(accountService.getId()).getVisa());
    }


    //Get we internet payment service by wallet (http://localhost:8082/weInternet/wallet/{phoneNumber})
    @GetMapping("/weInternet/wallet/{phoneNumber}")
    public String Recharge(@RequestBody Account account, @PathVariable("phoneNumber") int phoneNumber){
        if(!accountService.checkAccount(account))
            return "Invalid account";

        if (userService.getUserById(accountService.getId()).isFirstTransaction()) {
            userService.getUserById(accountService.getId()).setFirstTransaction(false);
        }
        double amount = 150;
        double fees = amount + (amount*0.1);
        paymentTransactionService.addPaymentTransaction(new PaymentTransaction(account.getEmail(), "We internet payment service", amount, fees));
        return weInternetPaymentServices.payInternet(phoneNumber, userService.getUserById(accountService.getId()).getWallet());
    }
}
