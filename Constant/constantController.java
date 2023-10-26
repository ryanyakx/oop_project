package com.g1t6.backend.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*" , allowedHeaders = "*")
@RestController
public class constantController {
    
    @Autowired
    constantService constantService;

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/changeMaxLoan")
    private String changeMaxLoan(@RequestBody constant constant){
        boolean status = constantService.changeMaxLoan(constant);
        if(status == true){
            return "Successfully Updated!";
        }else{
            return "Something went wrong!";
        }
    }
}
