package com.luv2code.springbootlibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springbootlibrary.dao.OrderRepository;
import com.luv2code.springbootlibrary.requestmodels.AddBookRequest;
import com.luv2code.springbootlibrary.requestmodels.OrderRequest;
import com.luv2code.springbootlibrary.service.AdminService;
import com.luv2code.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;
    private OrderRepository orderRepository;

    @Autowired
    public AdminController(AdminService adminService, OrderRepository orderRepository) {
        this.adminService = adminService;
        this.orderRepository = orderRepository;
    }

    @PutMapping("secure/decrease/book/quantity")
    public void decreaseBookQuantity(@RequestHeader(value = "Authorization") String token,
                                     @RequestParam Long bookId) throws Exception {
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if(admin == null || !admin.equals("admin")) {
            throw new Exception("Administration book only");
        }

        adminService.decreaseBookQuantity(bookId);
    }

    @PutMapping("secure/increase/book/quantity")
    public void increaseBookQuantity(@RequestHeader(value = "Authorization") String token,
                                     @RequestParam Long bookId) throws Exception {
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if(admin == null || !admin.equals("admin")) {
            throw new Exception("Administration book only");
        }

        adminService.increaseBookQuantity(bookId);
    }
    @PostMapping(
            value = "/secure/add/book",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void postBook(@RequestHeader(value = "Authorization") String token,
                         @RequestPart("bookData") String bookDataJson,
                         @RequestPart("file") MultipartFile file
    ) throws Exception {
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");

        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }

        ObjectMapper mapper = new ObjectMapper();
        AddBookRequest addBookRequest = mapper.readValue(bookDataJson, AddBookRequest.class);

        adminService.postBook(addBookRequest, file);
    }


    @DeleteMapping("/secure/delete/book")
    public void deleteBook(@RequestHeader(value = "Authorization") String token,
                         @RequestParam Long bookId
    ) throws Exception {
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");

        if(admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }

        adminService.deleteBook(bookId);
    }

    @DeleteMapping("/secure/delete/order")
    public void deleteOrder(@RequestHeader(value = "Authorization") String token,
                           @RequestParam Long orderId
    ) throws Exception {
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");

        if(admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }

        orderRepository.deleteById(orderId);
    }
//    @PostMapping(
//            value = "add/profile-image",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
//    )
//    public void uploadBookProfileImage(
//            @RequestParam("file") MultipartFile file,
//            @RequestParam String bookTitle
//    ) {
//        adminService.uploadBookProfileImage(bookTitle, file);
//    }

    @GetMapping("get/profile-image")
    public byte[] getBookProfileImage(@RequestParam String bookTitle) {
        return adminService.getBookProfileImage(bookTitle);
    }
}
