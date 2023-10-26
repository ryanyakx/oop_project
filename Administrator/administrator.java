// package com.g1t6.backend.Administrator;

// import java.io.Serializable;

// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.FetchType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.OneToOne;
// import javax.persistence.Table;

// import com.fasterxml.jackson.annotation.JsonProperty;
// import com.g1t6.backend.Borrower.*;


// @Entity
// @Table(name = "administrator")
// public class administrator implements Serializable {
//     @Id
//     @Column
//     private int administrator_ID;


//     @OneToOne(fetch = FetchType.LAZY , optional = false)
//     @JoinColumn(name = "borrower_ID" , referencedColumnName = "employee_ID")
//     private borrower borrower;
    
    
    
//     public administrator(){

//     }
    
    
//     public administrator(int administrator_ID , borrower borrower){
//         this.administrator_ID = administrator_ID;
//         this.borrower = borrower;
//     }

//     public administrator(borrower borrower){
//         this.borrower = borrower;
//     }


    