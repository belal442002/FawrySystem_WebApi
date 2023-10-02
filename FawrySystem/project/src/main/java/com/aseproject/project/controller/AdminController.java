package com.aseproject.project.controller;

import com.aseproject.project.Entity.Account;
import com.aseproject.project.Entity.PaymentTransaction;
import com.aseproject.project.Entity.Refund;
import com.aseproject.project.Entity.Wallet;
import com.aseproject.project.error.ExceptionMessage;
import com.aseproject.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private PaymentTransactionService paymentTransactionService;

    //Get all refunds (http://localhost:8082/listRefund)
    @GetMapping("/listRefund")
    public List<Refund> listRefund(){
        return adminService.listRefund();
    }

    //accept specific account refund (http://localhost:8082/acceptRefund/{email}/{amount})
    @PostMapping("/acceptRefund/{email}/{amount}")
    public String acceptRefund(@RequestBody Account account, @PathVariable("email") String email, @PathVariable("amount") double amount){
        if (!adminService.checkAdmin(account))
            return "You don not have authorization";
        if (!accountService.checkEmail(email))
            return "You can not send to wrong email";
        return adminService.acceptRefund(userService.getUserById(accountService.getId()), accountService.getAccountById(accountService.getId()) , amount);
    }

    //refuse specific account refund (http://localhost:8082/{email})
    @GetMapping("/refuseRefund/{email}")
    public String refuseRefund(@RequestBody Account account, @PathVariable("email") String email){
        if (!adminService.checkAdmin(account))
            return "You don not have authorization";
        if(!accountService.checkEmail(email))
            return "You can not send to wrong email";
        return adminService.refuseRefund(accountService.getAccountById(accountService.getId()));
    }

    //Get all wallet transaction (http://localhost:8082/listWalletTransaction)
    @GetMapping("/listWalletTransaction")
    public List<Wallet> listWalletTransaction(@RequestBody Account account) throws ExceptionMessage{
        if (!adminService.checkAdmin(account))
            throw new ExceptionMessage("You do not have authorization");
        return walletService.getWalletTransaction();
    }

    //Get all payment transaction (http://localhost:8082/listAllpaymentTransaction)
    @GetMapping("/listAllPaymentTransaction")
    public List<PaymentTransaction> getAllPaymentTransaction(@RequestBody Account account) throws ExceptionMessage{
        if (!adminService.checkAdmin(account))
            throw new ExceptionMessage("You do not have authorization");
        return paymentTransactionService.getAllPaymentTransaction();
    }
}
