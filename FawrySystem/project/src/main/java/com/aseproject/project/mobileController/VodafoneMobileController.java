package com.aseproject.project.mobileController;

import com.aseproject.project.Entity.MobileAccount;
import com.aseproject.project.Entity.PaymentTransaction;
import com.aseproject.project.MobileService.VodafoneMobileRechargeService;
import com.aseproject.project.service.AccountService;
import com.aseproject.project.service.PaymentTransactionService;
import com.aseproject.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class VodafoneMobileController {

    @Autowired
    private VodafoneMobileRechargeService vodafoneMobileRechargeService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentTransactionService paymentTransactionService;


    //Add discount to vodafone mobile recharge service (http://localhost:8082/vodafoneMobileDiscount/{discount})
    @PostMapping("/vodafoneMobileDiscount/{discount}")
    public String AddDiscount(@PathVariable("discount") double discount){
        return vodafoneMobileRechargeService.AddDiscount(discount);
    }

    //Get vodafone mobile recharge service and pay by visa (http://localhost:8082/vodafoneMobile/visa/{cardNumber}/{password})
    @GetMapping("/vodafoneMobile/visa/{cardNumber}/{password}")
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
        paymentTransactionService.addPaymentTransaction(new PaymentTransaction(mobileAccount.getAccount().getEmail(), "Vodafone mobile recharge service", amount, fees));
        return vodafoneMobileRechargeService.Recharge(mobileAccount.getMobile(), userService.getUserById(accountService.getId()).getVisa());
    }

    //Get vodafone mobile recharge service by wallet (http://localhost:8082/vodafoneMobile/wallet)
    @GetMapping("/vodafoneMobile/wallet")
    public String Recharge(@RequestBody MobileAccount mobileAccount){
        if(!accountService.checkAccount(mobileAccount.getAccount()))
            return "Invalid account";

        double amount = mobileAccount.getMobile().getAmount();
        double fees = amount + (amount*0.1);
        if (userService.getUserById(accountService.getId()).isFirstTransaction()) {
            mobileAccount.getMobile().setAmount(mobileAccount.getMobile().getAmount() * 0.1);
            userService.getUserById(accountService.getId()).setFirstTransaction(false);
        }
        paymentTransactionService.addPaymentTransaction(new PaymentTransaction(mobileAccount.getAccount().getEmail(), "Vodafone mobile recharge service", amount, fees));
        return vodafoneMobileRechargeService.Recharge(mobileAccount.getMobile(), userService.getUserById(accountService.getId()).getWallet());
    }
}
