package com.aseproject.project.mobileController;

import com.aseproject.project.Entity.MobileAccount;
import com.aseproject.project.Entity.PaymentTransaction;
import com.aseproject.project.MobileService.WeMobileRechargeService;
import com.aseproject.project.service.AccountService;
import com.aseproject.project.service.PaymentTransactionService;
import com.aseproject.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeMobileController {

    @Autowired
    private WeMobileRechargeService weMobileRechargeService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentTransactionService paymentTransactionService;

    //Add discount to we mobile recharge service (http://localhost:8082/weMobileDiscount/{discount})
    @PostMapping("/weMobileDiscount/{discount}")
    public String AddDiscount(@PathVariable("discount") double discount){
        return weMobileRechargeService.AddDiscount(discount);
    }

    //Get we mobile recharge service and pay by visa (http://localhost:8082/weMobile/visa/{cardNumber}/{password})
    @GetMapping("/weMobile/visa/{cardNumber}/{password}")
    public String Recharge(@RequestBody MobileAccount mobileAccount, @PathVariable("cardNumber") String cardNumber, @PathVariable("password") int password){
        if(!accountService.checkAccount(mobileAccount.getAccount()))
            return "Invalid account";
        if(cardNumber.equals("") || password == 0 || Integer.toString(password).length() < 4)
            return "Invalid visa account";

        double amount = mobileAccount.getMobile().getAmount();
        double fees = amount + (amount*0.1);
        if (userService.getUserById(accountService.getId()).isFirstTransaction()) {
            mobileAccount.getMobile().setAmount(mobileAccount.getMobile().getAmount() * 0.1);
            userService.getUserById(accountService.getId()).setFirstTransaction(false);
        }
        paymentTransactionService.addPaymentTransaction(new PaymentTransaction(mobileAccount.getAccount().getEmail(), "We mobile recharge service", amount, fees));
        return weMobileRechargeService.Recharge(mobileAccount.getMobile(), userService.getUserById(accountService.getId()).getVisa());
    }

    //Get vodafone mobile recharge service by wallet (http://localhost:8082/weMobile/wallet)
    @GetMapping("/weMobile/wallet")
    public String Recharge(@RequestBody MobileAccount mobileAccount){
        if(!accountService.checkAccount(mobileAccount.getAccount()))
            return "Invalid account";

        double amount = mobileAccount.getMobile().getAmount();
        double fees = amount + (amount*0.1);
        if (userService.getUserById(accountService.getId()).isFirstTransaction()) {
            mobileAccount.getMobile().setAmount(mobileAccount.getMobile().getAmount() * 0.1);
            userService.getUserById(accountService.getId()).setFirstTransaction(false);
        }
        paymentTransactionService.addPaymentTransaction(new PaymentTransaction(mobileAccount.getAccount().getEmail(), "We mobile recharge service", amount, fees));
        return weMobileRechargeService.Recharge(mobileAccount.getMobile(), userService.getUserById(accountService.getId()).getWallet());
    }
}
