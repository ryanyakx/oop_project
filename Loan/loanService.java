package com.g1t6.backend.Loan;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.time.ZoneId;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.g1t6.backend.Constant.*;
import com.g1t6.backend.Corporate_Pass.*;
import com.g1t6.backend.EmailSender.EmailSenderService;
import com.g1t6.backend.Membership.*;
import com.g1t6.backend.User.*;

@CrossOrigin(origins = "*" , allowedHeaders = "*")
@Service
public class loanService {
    
    @Autowired
    loanRepo loanRepo;

    @Autowired
    membershipRepo membershipRepo;

    @Autowired
    constantRepo constantRepo;

    @Autowired
    userRepo userRepo;

    @Autowired
    corporate_passRepo corporate_passRepo;

    @Autowired 
    EmailSenderService senderService; 
 
    @Autowired 
    public SimpleMailMessage template;



    public List<loan> getAll(){
        List<loan> return_list = new ArrayList<>();

        loanRepo.findAll().forEach(item -> return_list.add(item));

        return return_list;
    }

    

    public String createLoan(loanRequestBody jsonBody){
        String email = jsonBody.getEmail();
        String placeInterest = jsonBody.getPlaceInterest();
        int num_pass = jsonBody.getNumPass();
        String date = jsonBody.getDate();   //Date here refers to the date of visit

        String userName = "";
        List<user> all_user = new ArrayList<>();
        userRepo.findAll().forEach(user -> all_user.add(user));
        for(int i = 0 ; i < all_user.size() ; i++){
            if(all_user.get(i).getEmail().equals(email)){
                userName += all_user.get(i).getName();
            }
        }

        
        //return 1 -> Fail 1st Logic
        //return 2 -> Fail 2nd Logic
        //return 3 -> Fail 3rd Logic
        //return 0 -> Successful creation of loan


        /*
        1st Logic Check:
        If user input "num_pass" > max passes per loan (configurable) for selected place_of_interest(from json request body). 
        For example, if the max passes per loan for selected place_of_interest is 2, display exceeded the limit message if 
        user input 3 for "num_pass".
         */

        //1. Initialize max_pass_per_loan to be retrieved as int max_pass_per_loan = 0
         int max_pass_per_loan = 0;

        //2. Initialize membership list and Put all the membership rows into the list
        List<membership> all_membership_list = new ArrayList<>();
        membershipRepo.findAll().forEach(membership -> all_membership_list.add(membership));

        //3. Loop through the membership list and find the row that has the same "place_of_interest" from the json request body
        //and initialize the max_pass_per_loan to the value of that row
        for(int i = 0 ; i < all_membership_list.size() ; i++){
            if(all_membership_list.get(i).getPOI().equals(placeInterest)){
                max_pass_per_loan = all_membership_list.get(i).getMaxPassPerLoan();
            }
        }

        //4. Compare between "num_pass" from the json request body and max_pass_per_loan. If "num_pass" > max_pass_per_loan,
        //reject the loan creation and display "Exceed the limit of {max_pass_per_loan} passes per loan for {place_of_interest}
        if(num_pass > max_pass_per_loan){
            return "1Exceeded the limit of " + max_pass_per_loan + " passes per loan for " + "\"" + placeInterest + "\"";
        }else{
            /*
            2nd Logic Check:
            Check if the user has max loans (configurable) in that month. If the user already reached the
            limit, display already reached the limit message. For example, if the max loans is 2, if the user already
            has 2 on-going loans in July, user cannot apply any passes. If user has 1 loan in July, max he can loan in July
            is Max (2) - 1 = 1. However, he can loan maximum up to 2 loans in August.
            */

            //1. Get the max_loan_per_month value from the constant table. Denote this as int max_loan_per_month
            int max_loan_per_month = constantRepo.findById(1).get().getMaxLoanPerMonth();

            //2. Get all the Employee Objects in a list. Loop through them and get the row that matches the "email" from
            //the json request body. Fetch the employee_id for that particular user object. Denote this id as int employee_id
            int employee_id = 0;
            List<user> all_user_list = new ArrayList<>();
            userRepo.findAll().forEach(UserObject -> all_user_list.add(UserObject));
            for(int i = 0 ; i < all_user_list.size() ; i++){
                if(all_user_list.get(i).getEmail().equals(email)){
                    employee_id = all_user_list.get(i).getEmployeeID();
                }
            }
            
            
            //3. Get all the loan objects in a list. Loop through them and fetch the objects that matches the employee_id 
            //we have saved in Step 3. Save those loan objects in a particular list. Denote this list as 
            //List<loan> loan_match_employee_id
            List<loan> all_loan_list = new ArrayList<>();
            loanRepo.findAll().forEach(loanObject -> all_loan_list.add(loanObject));

            List<loan> loan_match_employee_id = new ArrayList<>();

            for(int i = 0 ; i < all_loan_list.size() ; i++){
                if(all_loan_list.get(i).getUser().getEmployeeID() == employee_id){
                    loan_match_employee_id.add(all_loan_list.get(i));
                }
            }
            
            

            //4. "date" of the TODAY and slice the string and get the year -> "2022" and month -> "10".
            //Denote each of them as -> String year = "2022" & String month = "10".
            LocalDateTime dateObj = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String todayDate = dateObj.format(format);
            String year = todayDate.split("-")[0];
            String month = todayDate.split("-")[1];
            

            //5. Loop through the loan_match_employee_id list and count how many rows that under date_Creation, the date
            //contains "2022" as year and "10" as month and the status is 0 -> user has loaned passes and have not visited
            //and returned the passes yet, which means the loan is on-going. Ignore status is 1 -> user has already returned 
            //the passes and loan has already finished. Denote the counts as int month_current_loan.
            int month_current_loan = 0;

            if(loan_match_employee_id.size() == 0){
                month_current_loan = 0;
            }else{
                Map<LocalDateTime , loan> map = new HashMap<>();
                for(int j = 0 ; j < loan_match_employee_id.size() ; j++){
                    loan loan_row = loan_match_employee_id.get(j);
                    LocalDateTime date_created = loan_row.getDateCreation();
                    map.put(date_created, loan_row);
                }
                
                System.out.println(map.size());
                Set<LocalDateTime> keys = map.keySet();
                

                for( LocalDateTime date_created : keys ){
                    loan loan_row = map.get(date_created);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    String date_created_format = formatter.format(date_created);
                    String year_from_db = date_created_format.split("-")[0];
                    String month_from_db = date_created_format.split("-")[1];
                    if( year.equals(year_from_db) && month.equals(month_from_db) && loan_row.getStatus() == 0){
                        month_current_loan += 1;
                    }
                }
            }
            
            System.out.println(month_current_loan);
            //7. If month_current_loan == max_loan_per_month, this means that user is unable to loan for any more passes in
            //that month anymore as the user has already hit the max number of loan for the month. Reject the loan creation
            //and display "Exceeded limit of {max_loan_per_month} loans per month".
            if(month_current_loan == max_loan_per_month){
                return "2Exceeded the limit of " + max_loan_per_month + " loans per month";
            }else{
                /*
                3rd Logic Check:
                Check if there are sufficient passes to be loaned for indicated date -> "date" received from the
                json request body.
                */

                //1. Loop through the all_membership_list and fetch the membership id that matches with the "place_of_interest"
                //from the json request body. Denote this id as int membership_poi_id
                int membership_poi_id = 0;
                
                for(int i = 0 ; i < all_membership_list.size() ; i++){
                    if(all_membership_list.get(i).getPOI().equals(placeInterest)){
                        membership_poi_id = all_membership_list.get(i).getID();
                    }
                }

                //2. Get all the corporate_pass objects/rows and save them into the list. Denote this list as
                //List<corporate_pass> all_corporate_pass_list
                List<corporate_pass> all_corporate_pass_list = new ArrayList<>();
                corporate_passRepo.findAll().forEach(cpObject -> all_corporate_pass_list.add(cpObject));

                
                //3. Loop through the all_corporate_pass_list and get the rows that match to the same membership_poi_id in Step 2
                //Denote this list as List<corporate_pass> corporate_pass_matched
                ArrayList<corporate_pass> corporate_pass_matched = new ArrayList<>();

                for(int i = 0 ; i < all_corporate_pass_list.size() ; i++){
                    corporate_pass cp_row = all_corporate_pass_list.get(i);
                    if(cp_row.getMemberObject().getID() == membership_poi_id && cp_row.getCardStatus() == 1){
                        corporate_pass_matched.add(cp_row);
                    }
                }

                //4. "date" from the json request body, slice the string and get the year -> "2022" and month -> "10" and
                //day -> "13". Denote each of them as -> String year = "2022" & String month = "10" & day = "13"
                String year_intended_visit = date.split("-")[0];
                String month_intended_visit = date.split("-")[1];
                String day_intended_visit = date.split("-")[2];

                //5. Loop through the all_loan_list and fetch rows that their date_Visit's year & month & day match exactly the same
                //as year & month & day extracted in Step4 AND the status of the loan is 0 -> the loan is on-going -> whoever
                //planning to visit places on that day haven't visited yet. Add these loan rows into the list. Denote this
                //list as List<loan> loan_match_date_visit.
                ArrayList<loan> loan_match_date_visit = new ArrayList<>();
                
                for(int i = 0 ; i < all_loan_list.size() ; i++){
                    loan loan_row = all_loan_list.get(i);
                    Date date_Visit = loan_row.getDateVisit();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String date_visit_format = formatter.format(date_Visit);
                    String year_visit = date_visit_format.split("-")[0];
                    String month_visit = date_visit_format.split("-")[1];
                    String day_visit = date_visit_format.split("-")[2];
                    if(year_intended_visit.equals(year_visit) && month_intended_visit.equals(month_visit) && day_intended_visit.equals(day_visit) && loan_row.getStatus() == 0){
                        loan_match_date_visit.add(loan_row);
                    }
                }
                
                //6.  Double loop through corporate_pass_matched against loan_match_date_visit. Every time a corporate_pass from
                //corporate_pass_matched matches with the same corporate_pass from loan_match_date_visit, remove that corporate_pass
                //from corporate_pass_matched list. The remaining corporate_pass in corporate_pass_matched list are the only
                //available corporate_pass that the user can borrow on THAT day the user wants to visit -> "date" from json
                //request body. 
                
                Iterator<corporate_pass> iter = corporate_pass_matched.iterator();
                while(iter.hasNext()){
                    String cardNumber = iter.next().getCardNumber();
                    for(int i = 0 ; i < loan_match_date_visit.size() ; i++){
                        String card_Number_Compare = loan_match_date_visit.get(i).getPass().getCardNumber();
                        if(cardNumber.equals(card_Number_Compare)){
                            iter.remove();
                        }
                    }
                }

                
                //7. Count how many available passes user can borrow for that day for indicated "place_of_interest". Denote
                //this as int available_pass
                int available_pass = 0;

                for(int i = 0 ; i < corporate_pass_matched.size() ; i++){
                    available_pass += 1;
                }
                
                //8.  If available_pass < num_pass (from json request body), reject the loan and display 
                //"There are insufficient passes to be loaned for indicated place of interest: {place_of_interest} at the moment"
                if(available_pass < num_pass){
                    return "3There are insufficient passes to be loaned for indicated place of interest: " + "\"" + placeInterest + "\"";
                }else{
                    /*
                    All logics have passed
                    Loop through the List<corporate_pass> corporate_pass_matched against "num_pass" and allow the user to borrow
                    the first {"num_pass"} passes in the list. Add the data into the loan table with the status -> 0 which
                    means that the loan is now on-going. Will become status of 1 if the user cancels the loan
                    */
                    user user = userRepo.findById(employee_id).get();
                    for(int i = 0 ; i < num_pass ; i++){
                        ZoneId defaultZoneId = ZoneId.systemDefault();
                        corporate_pass corporate_pass = corporate_pass_matched.get(i);
                        String cardNumber = corporate_pass.getCardNumber();
                        int status = 0;
                        int min = 0;
                        int max = 1000;
                        int ID = (int)Math.floor(Math.random()*(max-min+1)+min);

                        LocalDate date_visit = LocalDate.parse(date);
                        
                        //date_creation field
                        //Date date_creation_convert = Date.from(dateObj.atStartOfDay(defaultZoneId).toInstant());
                        LocalDateTime now = LocalDateTime.now();
                        
                        //date_visit field
                        Date date_visit_convert = Date.from(date_visit.atStartOfDay(defaultZoneId).toInstant());
                        
                        LocalDate return_date = date_visit.plusDays(1);
                        
                        Date return_date_convert = Date.from(return_date.atStartOfDay(defaultZoneId).toInstant());

                        loanRepo.save(new loan(ID , user, corporate_pass, now, date_visit_convert, return_date_convert, status));
                        
                        
                        String body = String.format(template.getText(), userName, placeInterest, date_visit, date_visit, date_visit, date_visit,cardNumber , placeInterest, placeInterest, placeInterest, placeInterest); 
                
                        try { 
                            senderService.sendMessageWithAttachment(email, "Booking Confirmation", body, "corporateLetter.pdf"); 
                        } catch (Throwable e) { 
                            System.out.println(e.getMessage()); 
                        }
                    }
                }

            }



        }

        
        return "Successfully created the loan for " + "\"" + placeInterest + "\"";

    }

    public void sendReminderEmail() { 
        List<loan> loanList = new ArrayList<>(); 
        loanRepo.findAll().forEach(item -> loanList.add(item)); 
        List<user> user_list = new ArrayList<>();
        userRepo.findAll().forEach(user -> user_list.add(user));

        Date currentDate = new Date(System.currentTimeMillis()); 
 
        for(loan oneLoan : loanList) { 
            Date expectedReturn = oneLoan.getDateExpectedReturn(); 
            Date dateVisit = oneLoan.getDateVisit(); 
            int status = oneLoan.getStatus(); 
            int employee_id = oneLoan.getUser().getEmployeeID();
            String email ="";
            for(int i = 0 ; i < user_list.size() ; i++){
                if(user_list.get(i).getEmployeeID() == employee_id){
                    email += user_list.get(i).getEmail();
                }
            }
            if (currentDate.after(expectedReturn) && status == 2) { 
                // send get request to User to get employee email with employee ID 
                
                senderService.sendSimpleMessage(email, "Return Reminder", "Please return your loan pass as soon as possible!"); 
 
            } 
            
            long diffInMs = dateVisit.getTime() - currentDate.getTime(); 
            long diffInDays = TimeUnit.DAYS.convert(diffInMs, TimeUnit.MILLISECONDS); 
 
            if (diffInDays >= 0 && diffInDays <= 1 && status == 0) { 
                // send get request to User to get employee email, employee name with employee ID 
                senderService.sendSimpleMessage(email, "Collection Reminder", "Please remember to collect your loan pass!"); 
            } 
        } 
         
    }
}
