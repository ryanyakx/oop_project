package com.g1t6.backend.Corporate_Pass;

import com.fasterxml.jackson.annotation.JsonProperty;

public class corporate_passRequestBody {
    private String card_Number;
    private String poi;
    private String type;
    private double fee;
    private int status;

    public corporate_passRequestBody(){

    }

    public corporate_passRequestBody(@JsonProperty("cardNumber") String cardNum,
                                     @JsonProperty("placeInterest") String poi,
                                     @JsonProperty("cardType") String type,
                                     @JsonProperty("replacementFee") double fee){
        this.card_Number = cardNum;
        this.poi = poi;
        this.type = type;
        this.fee = fee;
    }

    

    public String getCardNumber(){
        return this.card_Number;
    }

    public String getPOI(){
        return this.poi;
    }

    public String getType(){
        return this.type;
    }

    public double getFee(){
        return this.fee;
    }

    public int getStatus(){
        return this.status;
    }

}
