package com.g1t6.backend.Loan;

import com.g1t6.backend.Corporate_Pass.*;
import com.g1t6.backend.User.*;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table
public class loan implements Serializable {
    @Id
    @Column
    private int loan_ID;

    @ManyToOne
    @JoinColumn(name = "employee_ID" , nullable = false)
    private user user;

    @ManyToOne
    @JoinColumn(name = "card_Number" , nullable = false)
    private corporate_pass corporate_pass;

    @Column
    private LocalDateTime date_Creation;

    
    @Column
    private Date date_Visit;

    
    @Column
    private Date date_Expected_Return;

    
    @Column
    private int status;



    public loan() {

    }

    public loan(int id , user user , corporate_pass corporate_pass , LocalDateTime date_creation , Date date_visit,
                Date expectedReturn , int status){
        this.loan_ID = id;
        this.user = user;
        this.corporate_pass = corporate_pass;
        this.date_Creation = date_creation;
        this.date_Visit = date_visit;
        this.date_Expected_Return = expectedReturn;
        this.status = status;
    }
    
    
    public int getLoan_ID() {
        return this.loan_ID;
    }
    
    public user getUser(){
        return this.user;
    }

    public corporate_pass getPass(){
        return this.corporate_pass;
    }

    public LocalDateTime getDateCreation(){
        return this.date_Creation;
    }

    public Date getDateVisit(){
        return this.date_Visit;
    }

    public Date getDateExpectedReturn(){
        return this.date_Expected_Return;
    }

    public int getStatus(){
        return this.status;
    }
        
}
