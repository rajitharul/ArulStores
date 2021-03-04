package com.luv2code.ecommerce.dto;

import lombok.Data;

@Data
public class PurchaseResponse {

    //by the @Data lombock annotation it will only generate getter and setters for final fields
    private final String orderTrackingNumber;



}
