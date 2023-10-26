package com.g1t6.backend.Corporate_Pass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.g1t6.backend.Membership.membership;

@Entity
@Table
public class corporate_pass {
    @Id
    @Column
    private String card_Number;

    @ManyToOne
    @JoinColumn(name = "membership_ID" , nullable = false)
    private membership membership;

    @Column
    private String card_Type;

    @Column
    private double replacement_Fee;
    
    @Column
    private int status;

    public corporate_pass(){

    }

    public corporate_pass(String number , membership membership , String type , double fee , int status){
        this.card_Number = number;
        this.membership = membership;
        this.card_Type = type;
        this.replacement_Fee = fee;
        this.status = status;
    }
    

    public String getCardNumber(){
        return this.card_Number;
    }

    public membership getMemberObject(){
        return this.membership;
    }

    public String getCardType(){
        return this.card_Type;
    }

    public double getReplacementFee(){
        return this.replacement_Fee;
    }

    public int getCardStatus(){
        return this.status;
    }

    public void setCardStatus(int new_status){
        this.status = new_status;
    }


}   
