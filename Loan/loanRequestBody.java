package com.g1t6.backend.Loan;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class loanRequestBody {
    private String email;
    private String placeInterest;
    private int numPass;
    private String date;

    public loanRequestBody(){

    }

    public loanRequestBody(@JsonProperty("email") String email,
                           @JsonProperty("place_of_interest") String placeInterest,
                           @JsonProperty("num_pass") int numPass,
                           @JsonProperty("date") String date){
        this.email = email;
        this.placeInterest = placeInterest;
        this.numPass = numPass;
        this.date = date;
    }


    public String getEmail(){
        return this.email;
    }

    public String getPlaceInterest(){
        return this.placeInterest;
    }

    public int getNumPass(){
        return this.numPass;
    }

    public String getDate(){
        return this.date;
    }
    
    
}
