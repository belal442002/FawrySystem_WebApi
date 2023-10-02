package com.aseproject.project.service;

import com.aseproject.project.Entity.Account;
import com.aseproject.project.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService{
    @Autowired
    private AccountRepository accountRepository;
    private int id;

    public int getId() {
        return id;
    }

    public String AddAccount(Account account){
        List<Account> list = accountRepository.findAll();
        for(Account a : list){
            if (a.getUserName().equals(account.getUserName()) || a.getEmail().equals(account.getEmail()))
                return "This email is used before";
        }
        accountRepository.save(account);
        return "Account Added";
    }

    public Account getAccountById(int id){
        return accountRepository.findById(id).get();
    }

    public boolean checkAccount(Account account) {
        List<Account> list = accountRepository.findAll();
        for (Account a : list) {
            if (a.getEmail().equals(account.getEmail()) && a.getPassword().equals(account.getPassword())){
                this.id = a.getId();
                return true;
            }
        }
        return false;
    }

    public boolean checkEmail(String email) {
        List<Account> lis = accountRepository.findAll();
        for (Account a : lis) {
            if (a.getEmail().equals(email)){
                this.id = a.getId();
                return true;
            }
        }
        return false;
    }
    public List<Account> getAccount() {
        return accountRepository.findAll();
    }
}
