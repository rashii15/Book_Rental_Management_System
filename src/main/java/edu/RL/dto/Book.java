package edu.RL.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Book {
    private String bookId;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private Integer availableCopies;

}
