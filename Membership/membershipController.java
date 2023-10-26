package com.g1t6.backend.Membership;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*" , allowedHeaders = "*")
@RestController
public class membershipController {
    
    @Autowired
    membershipService membershipService;


    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/allMembership")
    private List<membership> getAll(){
        return membershipService.getAll();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/createMembership")
    private String createMembership(@RequestBody membership membership){
        int status = membershipService.createMembership(membership);
        if(status == 0){
            String place = membership.getPOI();
            return "Membership that contains: " + "\"" + place + "\"" + " already exists";
        }else{
            return "Successfully created";
        }
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @DeleteMapping("/disableMembership/{membership_name}")
    private String disableMembership(@PathVariable("membership_name") String membership_name){
        //String convert_membership_name = membership_name.replaceAll("\\s","").toLowerCase();
        int status = membershipService.disableMembership(membership_name);
        if(status == 0){
            return "Successfully disabled the following membership: " + "\"" + membership_name + "\"";
        }else{
            return "Something went wrong!";
        }
    }
    
}
