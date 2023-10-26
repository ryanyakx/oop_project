package com.g1t6.backend.Constant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table
public class constant {
    @Id
    @Column
    private int id;

    @Column
    private int max_loan_per_month;

    public constant(){

    }

    public constant(int id , int loan){
        this.id = id;
        this.max_loan_per_month = loan;
    }

    public constant(@JsonProperty("max_loan") int loan){
        this.max_loan_per_month = loan;
    }
    
    public int getConstantID(){
        return this.id;
    }

    public int getMaxLoanPerMonth(){
        return this.max_loan_per_month;
    }

    
}
