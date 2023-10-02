package com.aseproject.project.service;

import com.aseproject.project.Entity.Account;
import com.aseproject.project.Entity.Refund;
import com.aseproject.project.Entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private List<Refund> list = new ArrayList<>();

    public boolean checkAdmin(Account account){
        if(!(account.getEmail().equals("admin@system.com")) || !(account.getPassword().equals("admin123")))
            return false;
        return true;
    }

    public String addRefund(Refund refund){
        list.add(refund);
        return "Refund added successfully";
    }

    public List<Refund> listRefund(){
        return this.list;
    }

    public String acceptRefund(User user, Account account, double amount){
        user.getWallet().setAmount(amount);
        for (int i = 0; i < this.list.size(); i++){
            if (this.list.get(i).getAccount().getEmail().equals(account.getEmail()))
                this.list.remove(i);
        }
        return "Refund accepted successfully";
    }

    public String refuseRefund(Account account){
        for (int i = 0; i < this.list.size(); i++){
            if (this.list.get(i).getAccount().getEmail().equals(account.getEmail()))
                this.list.remove(i);
        }
        return "Refund refused successfully";
    }
}
