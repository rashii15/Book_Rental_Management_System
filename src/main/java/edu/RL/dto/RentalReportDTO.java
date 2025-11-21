package edu.RL.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class RentalReportDTO {
    private String rentalId;
    private String bookTitle;
    private String userName;
    private String rentalDate;
    private String dueDate;
    private String returnDate;
    private String status;
    private double fine;
}
