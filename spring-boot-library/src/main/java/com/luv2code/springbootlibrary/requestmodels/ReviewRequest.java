package com.luv2code.springbootlibrary.requestmodels;

import lombok.Data;

import java.util.Optional;

@Data
public class ReviewRequest {

    private Long bookId;
    private double rating;
    private Optional<String> reviewDescription;
}
