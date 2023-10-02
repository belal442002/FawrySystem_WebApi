package com.aseproject.project.controller;

import com.aseproject.project.Entity.Account;
import com.aseproject.project.service.AccountService;
import com.aseproject.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    //Put account in the database (http://localhost:8082/accounts)
    @PostMapping("/accounts")
    public String AddAccount(@RequestBody Account account){
        userService.addUser();
        return accountService.AddAccount(account);
    }

    //Get all accounts from the database (http://localhost:8082/accounts)
    @GetMapping("/accounts")
    public List<Account> getAccounts(){
        return accountService.getAccount();
    }
}
