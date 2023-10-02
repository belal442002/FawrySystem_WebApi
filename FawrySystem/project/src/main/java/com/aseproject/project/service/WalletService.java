package com.aseproject.project.service;

import com.aseproject.project.Entity.Wallet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WalletService {
    private List<Wallet> list = new ArrayList<>();

    public void addWalletTransaction(Wallet wallet){
        list.add(wallet);
    }

    public List<Wallet> getWalletTransaction(){
        return this.list;
    }
}
