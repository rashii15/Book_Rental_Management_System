package edu.RL.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Customer {
    private String customerId;
    private String cusName;
    private String contact;
    private String email;
    private LocalDate DOB;
    private String address;
    private String postalCode;
}
