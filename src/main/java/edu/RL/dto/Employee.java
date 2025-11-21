package edu.RL.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Employee {
    private String employeeId;
    private String username;
    private String password;
    private String role;

}
