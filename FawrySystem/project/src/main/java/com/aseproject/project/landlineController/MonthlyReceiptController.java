package com.aseproject.project.landlineController;

import com.aseproject.project.Entity.Account;
import com.aseproject.project.Entity.PaymentTransaction;
import com.aseproject.project.landlineService.MonthlyReceipt;
import com.aseproject.project.service.AccountService;
import com.aseproject.project.service.PaymentTransactionService;
import com.aseproject.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MonthlyReceiptController {

    @Autowired
    private MonthlyReceipt monthlyReceipt;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentTransactionService paymentTransactionService;

    //Add discount to monthly receipt landline service (http://localhost:8082/monthlyReceiptDiscount/{discount})
    @PostMapping("/monthlyReceiptDiscount/{discount}")
    public String AddDiscount(@PathVariable("discount") double discount){
        return monthlyReceipt.AddDiscount(discount);
    }

    //Get monthly receipt service and pay by visa (http://localhost:8082/landlineMonthlyReceipt/visa/{landlineNumber}/{cardNumber}/{password})
    @GetMapping("/landlineMonthlyReceipt/visa/{landlineNumber}/{cardNumber}/{password}")
    public String Recharge(@RequestBody Account account, @PathVariable("landlineNumber") int landlineNumber, @PathVariable("cardNumber") String cardNumber, @PathVariable("password") int password){
        if(!accountService.checkAccount(account))
            return "Invalid account";
        if(cardNumber.equals("") || password == 0 || Integer.toString(password).length() < 4)
            return "Invalid visa account";

        if (userService.getUserById(accountService.getId()).isFirstTransaction()) {
            monthlyReceipt.setFirstTransactionDiscount(0.1);
            userService.getUserById(accountService.getId()).setFirstTransaction(false);
        }
        double amount = 150;
        double fees = amount + (amount * 0.1);
        paymentTransactionService.addPaymentTransaction(new PaymentTransaction(account.getEmail(), "Monthly receipt landline service", amount, fees));
        return monthlyReceipt.payReceipt(landlineNumber, userService.getUserById(accountService.getId()).getVisa());
    }

    //Get monthly receipt service by wallet (http://localhost:8082/landlineMonthlyReceipt/wallet/{landlineNumber})
    @GetMapping("/landlineMonthlyReceipt/wallet/{landlineNumber}")
    public String Recharge(@RequestBody Account account, @PathVariable("landlineNumber") int landlineNumber){
        if(!accountService.checkAccount(account))
            return "Invalid account";

        if (userService.getUserById(accountService.getId()).isFirstTransaction()) {
            monthlyReceipt.setFirstTransactionDiscount(0.1);
            userService.getUserById(accountService.getId()).setFirstTransaction(false);
        }
        double amount = 150;
        double fees = amount + (amount * 0.1);
        paymentTransactionService.addPaymentTransaction(new PaymentTransaction(account.getEmail(), "Monthly receipt landline service", amount, fees));
        return monthlyReceipt.payReceipt(landlineNumber, userService.getUserById(accountService.getId()).getWallet());
    }
}
