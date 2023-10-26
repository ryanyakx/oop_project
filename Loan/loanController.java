package com.g1t6.backend.Loan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*" , allowedHeaders = "*")
@RestController
public class loanController {
    

    @Autowired
    loanService loanService;

    
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/getAllLoan")
    private List<loan> getAll(){
        return loanService.getAll();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/createLoan")
    private String createLoan(@RequestBody loanRequestBody jsonBody){
        String status = loanService.createLoan(jsonBody);
        String subString = status.substring(0 , 1);
        if(subString.equals("1")){
            return status.substring(1, status.length());
        }else if(subString.equals("2")){
            return status.substring(1, status.length());
        }else if(subString.equals("3")){
            return status.substring(1, status.length());
        }else{
            return status;
        }
    }
    
    
    
    

}
