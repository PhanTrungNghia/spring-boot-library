package com.luv2code.springbootlibrary.requestmodels;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AddBookRequest {
    private String title;
    private String author;
    private String description;
    private int copies;
    private String category;
    private String img;
}
