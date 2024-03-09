package com.luv2code.springbootlibrary.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "book")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "description")
    private String description;
    @Column(name = "copies")
    private int copies;
    @Column(name = "copies_available")
    private int copiesAvailable;
    @Column(name = "category")
    private String category;
    @Column(name = "img")
    private String img;
    @Column(name = "price")
    private Double price;
    @Column(name="profile_image_id")
    private String profileImageId;

    public Book() {
    }

    public Book(String title, String author, String description, int copies, int copiesAvailable, String category, String img, String profileImageId) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.copies = copies;
        this.copiesAvailable = copiesAvailable;
        this.category = category;
        this.img = img;
        this.profileImageId = profileImageId;
    }
}
