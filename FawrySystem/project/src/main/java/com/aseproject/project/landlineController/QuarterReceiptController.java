package com.aseproject.project.landlineController;

import com.aseproject.project.Entity.Account;
import com.aseproject.project.Entity.PaymentTransaction;
import com.aseproject.project.landlineService.QuarterReceipt;
import com.aseproject.project.service.AccountService;
import com.aseproject.project.service.PaymentTransactionService;
import com.aseproject.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuarterReceiptController {

    @Autowired
    private QuarterReceipt quarterReceipt;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentTransactionService paymentTransactionService;

    //Add discount to Quarter receipt landline service (http://localhost:8082/quarterReceiptDiscount/{discount})
    @PostMapping("/quarterReceiptDiscount/{discount}")
    public String AddDiscount(@PathVariable("discount") double discount){
        return quarterReceipt.AddDiscount(discount);
    }

    //Get quarter receipt service and pay by visa (http://localhost:8082/landlineQuarterReceipt/visa/{landlineNumber}/{cardNumber}/{password})
    @GetMapping("/landlineQuarterReceipt/visa/{landlineNumber}/{cardNumber}/{password}")
    public String Recharge(@RequestBody Account account, @PathVariable("landlineNumber") int landlineNumber, @PathVariable("cardNumber") String cardNumber, @PathVariable("password") int password){
        if(!accountService.checkAccount(account))
            return "Invalid account";
        if(cardNumber.equals("") || password == 0 || Integer.toString(password).length() < 4)
            return "Invalid visa account";

        if (userService.getUserById(accountService.getId()).isFirstTransaction()) {
            quarterReceipt.setFirstTransactionDiscount(0.1);
            userService.getUserById(accountService.getId()).setFirstTransaction(false);
        }
        double amount = 300;
        double fees = amount + (amount * 0.1);
        paymentTransactionService.addPaymentTransaction(new PaymentTransaction(account.getEmail(), "Quarter receipt landline service", amount, amount*0.1));
        return quarterReceipt.payReceipt(landlineNumber, userService.getUserById(accountService.getId()).getVisa());
    }

    //Get quarter receipt service by wallet (http://localhost:8082/landlineQuarterReceipt/wallet/{landlineNumber})
    @GetMapping("/landlineQuarterReceipt/wallet/{landlineNumber}")
    public String Recharge(@RequestBody Account account, @PathVariable("landlineNumber") int landlineNumber){
        if(!accountService.checkAccount(account))
            return "Invalid account";

        if (userService.getUserById(accountService.getId()).isFirstTransaction()) {
            quarterReceipt.setFirstTransactionDiscount(0.1);
            userService.getUserById(accountService.getId()).setFirstTransaction(false);
        }
        double amount = 300;
        double fees = amount + (amount * 0.1);
        paymentTransactionService.addPaymentTransaction(new PaymentTransaction(account.getEmail(), "Quarter receipt landline service", amount, fees));
        return quarterReceipt.payReceipt(landlineNumber, userService.getUserById(accountService.getId()).getWallet());
    }
}
