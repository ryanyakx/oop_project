package com.g1t6.backend.User;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;



@Entity
@Table(name = "User")
public class user implements Serializable {
    @Id
    @Column
    private int employee_ID;
    
    @Column
    private String name;

    @Column
    private String email;

    @Column
    private int contact_Num;

    @Column
    private String password;

    @Column
    private int is_admin;

    @Column
    private int status;



    public user(){

    }

    public user(@JsonProperty("employeeID") int ID,
                    @JsonProperty("name") String name,
                    @JsonProperty("email") String email,
                    @JsonProperty("contactNum") int contact,
                    @JsonProperty("password") String password,
                    @JsonProperty("isAdmin") int isAdmin,
                    @JsonProperty("status") int status){
        this.employee_ID = ID;
        this.name = name;
        this.email = email;
        this.contact_Num = contact;
        this.password = password;
        this.is_admin = isAdmin;
        this.status = status;
    }
    
    public int getEmployeeID(){
        return this.employee_ID;
    }

    public void setEmployeeID(int ID){
        this.employee_ID = ID;
    }

    public String getName(){
        return this.name;
    }

    public String setName(String name){
        return this.name = name;
    }

    public String getEmail(){
        return this.email;
    }

    public String setEmail(String email){
        return this.email = email;
    }

    public int getContactNum(){
        return this.contact_Num;
    }

    public int setContactNum(int contact){
        return this.contact_Num = contact;
    }

    public String getPassword(){
        return this.password;
    }

    public String setPassword(String password){
        return this.password = password;
    }

    public int getAdminStatus(){
        return this.is_admin;
    }

    public int setAdminStatus(int isAdmin){
        return this.is_admin = isAdmin;
    }

    public int getUserStatus(){
        return this.status;
    }

    public int setUserStatus(int status){
        return this.status = status;
    }

    public int getStatus(){
        return this.status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    
}