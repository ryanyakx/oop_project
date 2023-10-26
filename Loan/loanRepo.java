package com.g1t6.backend.Loan;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface loanRepo extends CrudRepository<loan , Integer>{
    @Transactional 
    @Modifying 
    @Query("UPDATE loan l SET l.status = :status WHERE l.loan_ID = :loan_ID") 
    public void setLoanStatus(@Param("status") int status, @Param("loan_ID") int loan_ID);

    
}
