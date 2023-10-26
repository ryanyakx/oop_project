package com.g1t6.backend.Membership;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g1t6.backend.Corporate_Pass.*;

@Service
public class membershipService {
    
    @Autowired
    membershipRepo membershipRepo;

    @Autowired
    corporate_passRepo corporate_passRepo;

    public List<membership> getAll(){
        List<membership> all_membership = new ArrayList<>();
        membershipRepo.findAll().forEach(member -> all_membership.add(member));
        return all_membership;
    }


    public int createMembership(membership membership){
        String name = membership.getName();
        String placeInterest = membership.getPOI();
        int max = membership.getMaxPassPerLoan();
        int status = 1;
        int checkCounter = 0;
        List<membership> all_membership = new ArrayList<>();
        membershipRepo.findAll().forEach(member -> all_membership.add(member));
        for(int i = 0 ; i < all_membership.size() ; i++){
            if(all_membership.get(i).getPOI().equals(placeInterest)){
                checkCounter += 1;
            }
        }
        
        if(checkCounter == 1){
            return 0;
        }else{
            membershipRepo.save(new membership(name, placeInterest, max , status));
            return 1;
        }
    }

    public int disableMembership(String membership_name){
        List<membership> all_membership_list = new ArrayList<>();
        membershipRepo.findAll().forEach(membership -> all_membership_list.add(membership));

        List<corporate_pass> all_corporate_pass_list = new ArrayList<>();
        corporate_passRepo.findAll().forEach(item -> all_corporate_pass_list.add(item));

        List<membership> membership_name_list = new ArrayList<>();

        for(int i = 0 ; i < all_membership_list.size() ; i++){
            //String name = all_membership_list.get(i).getName().replaceAll("\\s","").toLowerCase();
            if(all_membership_list.get(i).getName().equals(membership_name)){
                membership_name_list.add(all_membership_list.get(i));
            }
        }
        
        for(int i = 0 ; i < membership_name_list.size() ; i++){
            membership membership = membership_name_list.get(i);
            int id = membership.getID();
            String name = membership.getName();
            String place_of_interest = membership.getPOI();
            int max_pass = membership.getMaxPassPerLoan();
            int new_status = 0;
            membershipRepo.save(new membership( id , name, place_of_interest, max_pass , new_status));
            for(int j = 0 ; j < all_corporate_pass_list.size() ; j++){
                corporate_pass corporate_pass = all_corporate_pass_list.get(j);
                if(id == corporate_pass.getMemberObject().getID()){
                    corporate_pass.setCardStatus(0);
                }
            }
        }

        return 0;
        

    }
}
