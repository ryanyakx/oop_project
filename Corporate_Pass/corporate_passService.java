package com.g1t6.backend.Corporate_Pass;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g1t6.backend.Membership.*;

@Service
public class corporate_passService {
    @Autowired
    corporate_passRepo corporate_passRepo;

    @Autowired
    membershipRepo membershipRepo;
    public List<corporate_pass> getAll(){
        List<corporate_pass> returnList = new ArrayList<>();

        corporate_passRepo.findAll().forEach(item -> returnList.add(item));

        return returnList;
    }

    
    public boolean createPass(corporate_passRequestBody jsonBody){
        String cardNumber = jsonBody.getCardNumber();
        String place_of_interest = jsonBody.getPOI();
        String cardType = jsonBody.getType();
        double replacementFee = jsonBody.getFee();

        //Get the membership object that matches the place of interest passed from the json request body
        List<membership> all_membership = new ArrayList<>();
        membershipRepo.findAll().forEach(item -> all_membership.add(item));

        int get_membership_id = 0;
        
        //Loop through the all_membership list and fetch the membership id that matches the place of interest passed from the
        //json body
        for(int i = 0 ; i < all_membership.size() ; i++){
            if(all_membership.get(i).getPOI().replaceAll("\\s", "").toLowerCase().equals(place_of_interest.replaceAll("\\s", "").toLowerCase()) ){
                get_membership_id = all_membership.get(i).getID();
            }
        }

        //Using the membership id fetched, get that membership object
        membership membership = membershipRepo.findById(get_membership_id).get();

        //When we first create the corporate pass, the status is auto 1 -> available
        int status = 1;
        
        //Add new corporate pass into the database
        corporate_passRepo.save(new corporate_pass(cardNumber, membership, cardType, replacementFee, status) );

        return true;
        
    }

    public boolean cardLost(String cardNumber){
        List<corporate_pass> all_corporate_pass = new ArrayList<>();
        corporate_passRepo.findAll().forEach(cp -> all_corporate_pass.add(cp));
        for(int i = 0 ; i < all_corporate_pass.size() ; i++){
            if(all_corporate_pass.get(i).getCardNumber().equals(cardNumber)){
                corporate_pass corporate_pass_row = all_corporate_pass.get(i);
                membership membership = corporate_pass_row.getMemberObject();
                String type = corporate_pass_row.getCardType();
                double replacementFee = corporate_pass_row.getReplacementFee();
                int new_status = 0;
                corporate_passRepo.save(new corporate_pass(cardNumber, membership, type, replacementFee, new_status));
            }
        }
        return true;
    }

    
    
    

    
}
