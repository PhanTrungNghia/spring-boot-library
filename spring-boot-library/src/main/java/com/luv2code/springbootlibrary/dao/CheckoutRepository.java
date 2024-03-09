package com.luv2code.springbootlibrary.dao;

import com.luv2code.springbootlibrary.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
    Checkout findByUserEmailAndBookId (String userEmail, Long bookId);

    List<Checkout> findByUserEmail (String userEmail);

    @Modifying
    @Query("delete from Checkout c where c.bookId = :bookId")
    void deleteAllByBookId(@Param("bookId") Long bookId);
}
