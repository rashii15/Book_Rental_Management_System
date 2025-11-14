package edu.RL.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private String userId;
    private String username;
    private String password;
    private String role;
    private String status;
}
