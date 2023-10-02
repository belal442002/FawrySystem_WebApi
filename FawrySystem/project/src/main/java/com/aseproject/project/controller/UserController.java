package com.aseproject.project.controller;

import com.aseproject.project.Entity.Account;
import com.aseproject.project.Entity.Refund;
import com.aseproject.project.Entity.Wallet;
import com.aseproject.project.InternetService.EtisalatInternetPaymentServices;
import com.aseproject.project.InternetService.OrangeInternetPaymentServices;
import com.aseproject.project.InternetService.VodafoneInternetPaymentServices;
import com.aseproject.project.InternetService.WeInternetPaymentServices;
import com.aseproject.project.MobileService.*;
import com.aseproject.project.landlineService.MonthlyReceipt;
import com.aseproject.project.landlineService.QuarterReceipt;
import com.aseproject.project.service.AccountService;
import com.aseproject.project.service.AdminService;
import com.aseproject.project.service.UserService;
import com.aseproject.project.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private EtisalatMobileRechargeService etisalatMobileRechargeService;
    @Autowired
    private EtisalatInternetPaymentServices etisalatInternetPaymentServices;
    @Autowired
    private VodafoneMobileRechargeService vodafoneMobileRechargeService;
    @Autowired
    private VodafoneInternetPaymentServices vodafoneInternetPaymentServices;
    @Autowired
    private WeMobileRechargeService weMobileRechargeService;
    @Autowired
    private WeInternetPaymentServices weInternetPaymentServices;
    @Autowired
    private OrangeMobileRechargeService orangeMobileRechargeService;
    @Autowired
    private OrangeInternetPaymentServices orangeInternetPaymentServices;
    @Autowired
    private MonthlyReceipt monthlyReceipt;
    @Autowired
    private QuarterReceipt quarterReceipt;
    @Autowired
    private WalletService walletService;


    //To add funds to wallet (http://localhost:8082/addFundsToWallet/{amount})
    @PostMapping("/addFundsToWallet/{amount}")
    public String AddFundsToWallet(@RequestBody Account account, @PathVariable("amount") double amount){
        if(!accountService.checkAccount(account))
            return "Invalid account";
        Wallet wallet = new Wallet(account.getEmail(), amount, userService.getUserById(accountService.getId()).getWallet().getAmount() + " LE");
        walletService.addWalletTransaction(wallet);
        return userService.addFundsToWallet(accountService.getId(), amount);
    }

    //Add Refund and send it to admin (http://localhost:8082/addRefund)
    @PostMapping("/addRefund")
    public String AddRefund(@RequestBody Refund refund){
        if(!accountService.checkAccount(refund.getAccount()))
            return "Invalid account";
        return adminService.addRefund(refund);
    }

    //Search for specific service (http://localhost:8082/search/{query})
    @GetMapping("/search/{query}")
    public String Search(@PathVariable("query") String query){
        return userService.search(query);
    }

    //Check if there any discount on etisalat services (http://localhost:8082/etisalatDiscount)
    @GetMapping("/etisalatDiscount")
    public String checkEtisalatDiscount(){
        return userService.checkEtisalatDiscount(etisalatMobileRechargeService.getDiscount(), etisalatInternetPaymentServices.getDiscount());
    }

    //Check if there any discount on vodafone services (http://localhost:8082/vodafoneDiscount)
    @GetMapping("/vodafoneDiscount")
    public String checkVodafoneDiscount(){
        return userService.checkVodafoneDiscount(vodafoneMobileRechargeService.getDiscount(), vodafoneInternetPaymentServices.getDiscount());
    }

    //Check if there any discount on we services (http://localhost:8082/weDiscount)
    @GetMapping("/weDiscount")
    public String checkWeDiscount(){
        return userService.checkWeDiscount(weMobileRechargeService.getDiscount(), weInternetPaymentServices.getDiscount());
    }

    //Check if there any discount on orange services (http://localhost:8082/orangeDiscount)
    @GetMapping("/orangeDiscount")
    public String checkOrangeDiscount(){
        return userService.checkOrangeDiscount(orangeMobileRechargeService.getDiscount(), orangeInternetPaymentServices.getDiscount());
    }

    //Check if there any discount on landline services (http://localhost:8082/landlineDiscount)
    @GetMapping("/landlineDiscount")
    public String checkLandlineDiscount(){
        return userService.checkLandlineDiscount(monthlyReceipt.getDiscount(), quarterReceipt.getDiscount());
    }
}
