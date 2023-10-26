package com.g1t6.backend.Membership;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table
public class membership {
    
    @Id
    @Column
    private int membership_ID;
    
    @Column
    private String name;
    
    @Column
    private String place_of_interest;

    @Column
    private int max_pass_per_loan;

    @Column
    private int status;


    public membership(){

    }

    public membership(int id , String name , String poi , int max , int status){
        this.membership_ID = id;
        this.name = name;
        this.place_of_interest = poi;
        this.max_pass_per_loan = max;
        this.status = status;
    }

    public membership(String name , String poi , int max , int status){
        this.name = name;
        this.place_of_interest = poi;
        this.max_pass_per_loan = max;
        this.status = status;
    }

    public membership(@JsonProperty("name") String name,
                      @JsonProperty("placeInterest") String placeInterest,
                      @JsonProperty("max_pass") int max){
        this.name = name;
        this.place_of_interest = placeInterest;
        this.max_pass_per_loan = max;

    }


    public int getID(){
        return this.membership_ID;
    }

    public String getName(){
        return this.name;
    }

    public String getPOI(){
        return this.place_of_interest;
    }

    public int getMaxPassPerLoan(){
        return this.max_pass_per_loan;
    }

    public int getMembershipStatus(){
        return this.status;
    }

    

}
