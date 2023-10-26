package com.g1t6.backend.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class constantService {
    
    @Autowired
    constantRepo constantRepo;


    public boolean changeMaxLoan(constant constant){
        int max_loan_per_month = constant.getMaxLoanPerMonth();
        constantRepo.save(new constant(1 , max_loan_per_month));
        return true;
    }
}
