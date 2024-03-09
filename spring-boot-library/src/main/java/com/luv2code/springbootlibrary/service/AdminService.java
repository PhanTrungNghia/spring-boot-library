package com.luv2code.springbootlibrary.service;

import com.luv2code.springbootlibrary.dao.BookRepository;
import com.luv2code.springbootlibrary.dao.CheckoutRepository;
import com.luv2code.springbootlibrary.dao.ReviewRepository;
import com.luv2code.springbootlibrary.entity.Book;
import com.luv2code.springbootlibrary.exception.ResourceNotFoundException;
import com.luv2code.springbootlibrary.requestmodels.AddBookRequest;
import com.luv2code.springbootlibrary.s3.S3Buckets;
import com.luv2code.springbootlibrary.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AdminService {

    private BookRepository bookRepository;
    private CheckoutRepository checkoutRepository;
    private ReviewRepository reviewRepository;
    private S3Service s3Service;
    private S3Buckets s3Buckets;

    @Autowired
    public AdminService(BookRepository bookRepository, CheckoutRepository checkoutRepository,
                        ReviewRepository reviewRepository, S3Service s3Service, S3Buckets s3Buckets) {
        this.bookRepository = bookRepository;
        this.checkoutRepository = checkoutRepository;
        this.reviewRepository = reviewRepository;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
    }

    public void deleteBook(Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        if(book.isEmpty()) {
            throw new Exception("Book not found");
        }

        checkoutRepository.deleteAllByBookId(bookId);
        reviewRepository.deleteAllByBookId(bookId);
        bookRepository.delete(book.get());
    }

    public void decreaseBookQuantity(Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        if(book.isEmpty() || book.get().getCopiesAvailable() <= 0 || book.get().getCopies() <= 0) {
            throw new Exception("Book not found or quantity locked");
        }

        book.get().setCopies(book.get().getCopies() - 1);
        book.get().setCopiesAvailable(book.get().getCopiesAvailable() - 1);

        bookRepository.save(book.get());
    }

    public void increaseBookQuantity(Long bookId) throws Exception {
        Optional<Book> book = bookRepository.findById(bookId);

        if(!book.isPresent()) {
            throw new Exception("Book not found");
        }

        book.get().setCopiesAvailable(book.get().getCopiesAvailable() + 1);
        book.get().setCopies(book.get().getCopies() + 1);

        bookRepository.save(book.get());
    }

    public void postBook(AddBookRequest addBookRequest, MultipartFile file) {
        String profileImageId = UUID.randomUUID().toString();

        uploadBookProfileImage(addBookRequest.getTitle(), file, profileImageId);

        Book book = new Book();

        book.setTitle(addBookRequest.getTitle());
        book.setAuthor(addBookRequest.getAuthor());
        book.setDescription(addBookRequest.getDescription());
        book.setCopies(addBookRequest.getCopies());
        book.setCopiesAvailable(addBookRequest.getCopies());
        book.setCategory(addBookRequest.getCategory());
        book.setImg(addBookRequest.getImg());
        book.setProfileImageId(profileImageId);

        bookRepository.save(book);
    }

    public void uploadBookProfileImage(String bookTitle, MultipartFile file, String profileImageId) {

        try {
            s3Service.putObject(
                    "team12hau",
                    "profile-images/%s/%s".formatted(bookTitle, profileImageId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // TODO: Update profileImageId to MySQL
//        bookRepository.updateBookProfileImageIdByTitle(addBookRequest.getTitle);
    }

    public byte[] getBookProfileImage(String bookTitle) {

        Optional<Book> book = bookRepository.findByTitle(bookTitle);

        if(book.isEmpty()){
            throw new ResourceNotFoundException(
                    "book with id [%s] not found".formatted(bookTitle)
            );
        }

        // TODO: Check if profileImageId is empty of null
        if(book.get().getProfileImageId().isBlank()) {
            throw new ResourceNotFoundException(
                    "book with id [%s] profile image not found".formatted(bookTitle)
            );
        }

        byte[] profileImage = s3Service.getObject(
                s3Buckets.getBucket(),
                "profile-images/%s/%s".formatted(bookTitle, book.get().getProfileImageId())
        );

        return profileImage;
    }
}
