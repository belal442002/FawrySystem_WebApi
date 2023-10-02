package com.aseproject.project.service;

import com.aseproject.project.Entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserService {
    private List<User> list = new ArrayList<>();

    public void addUser(){
        list.add(new User());
    }

    public User getUserById(int id){
        for(User user : this.list){
            if(user.getId() == id)
                return user;
        }
        return null;
    }

    public String addFundsToWallet(int id, double amount){
        User user = this.getUserById(id);
        double withdraw = user.getVisa().Withdraw(amount);
        if(withdraw == 0)
            return "You can not withdraw this amount";
        return user.getWallet().Deposit(withdraw) + "\nVisa: " + user.getVisa().printAmount();
    }

    public String search(String query){

        if(query.equals("Mobile recharge services") || query.equals("mobile recharge") || query.equals("mobile"))
            return "Mobile recharge services: \n1- Vodafone\n2- Etisalat\n3- Orange\n4- We\n";

        else if(query.equals("Internet Payment services") || query.equals("internet payment") || query.equals("internet"))
            return "Internet Payment services: \n1- Vodafone\n2- Etisalat\n3- Orange\n4- We\n";

        else if(query.equals("Landline services") || query.equals("landline services") || query.equals("landline"))
            return "Landline services: \n1- Monthly receipt\n2- Quarter receipt";

        else if(query.equals("Donations") || query.equals("donations") || query.equals("donation"))
            return "Donations :\n1- Cancer Hospital\n2- Schools\n3- NGOs";

        return "No result matched";
    }

    public String checkEtisalatDiscount(double EMRS_Discount, double EIPS_Discount){
        String EDiscount = "";
        if(EMRS_Discount > 0)
            EDiscount += "Mobile recharge services: discount by" + (EMRS_Discount*100) + "%";
        if(EIPS_Discount > 0)
            EDiscount += "\nInternet payment services: discount by" + (EIPS_Discount*100) + "%";
        if(!(EDiscount.equals("")))
            return EDiscount;
        return "There is no discount";
    }

    public String checkVodafoneDiscount(double VMRS_Discount, double VIPS_Discount){
        String VDiscount = "";
        if(VMRS_Discount > 0)
            VDiscount += "Mobile recharge services: discount by" + (VMRS_Discount*100) + "%";
        if(VIPS_Discount > 0)
            VDiscount += "\nInternet payment services: discount by" + (VIPS_Discount*100) + "%";
        if(!(VDiscount.equals("")))
            return VDiscount;
        return "There is no discount";
    }

    public String checkWeDiscount(double WMRS_Discount, double WIPS_Discount){
        String WDiscount = "";
        if(WMRS_Discount > 0)
            WDiscount += "Mobile recharge services: discount by" + (WMRS_Discount*100) + "%";
        if(WIPS_Discount > 0)
            WDiscount += "\nInternet payment services: discount by" + (WIPS_Discount*100) + "%";
        if(!(WDiscount.equals("")))
            return WDiscount;
        return "There is no discount";
    }

    public String checkOrangeDiscount(double OMRS_Discount, double OIPS_Discount){
        String ODiscount = "";
        if(OMRS_Discount > 0)
            ODiscount += "Mobile recharge services: discount by" + (OMRS_Discount*100) + "%";
        if(OIPS_Discount > 0)
            ODiscount += "\nInternet payment services: discount by" + (OIPS_Discount*100) + "%";
        if(!(ODiscount.equals("")))
            return ODiscount;
        return "There is no discount";
    }

    public String checkLandlineDiscount(double MR_Discount, double QR_Discount){
        String LLDiscount = "";
        if(MR_Discount > 0)
            LLDiscount += "Land line services: discount by" + (MR_Discount*100) + "% on monthly receipt";
        if(QR_Discount > 0)
            LLDiscount += "\nLand line services : discount by" + (QR_Discount*100) + "% on Quarter receipt";
        if(!(LLDiscount.equals("")))
            return LLDiscount;
        return "There is no discount";
    }
}
