package com.backend.academicsys.entity;

import java.time.Year;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Textbook {
    private int textbookId;
    private String bookTitle;
    private String author;
    private String edition;
    private String isbn;
    private String publisher;
    private Year publicationYear;
    private int courseId;
    private int price;
    private String bookDescription;
    private String bookPhoto;
    private int stock;
    private int booked;
}