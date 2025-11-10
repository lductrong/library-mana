package com.trong.library.controller;

import com.trong.library.dto.LibraryStats;
import com.trong.library.service.BookService;
import com.trong.library.service.BorrowHistoryService;
import com.trong.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class LibraryStatsController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private BorrowHistoryService borrowHistoryService;

    @GetMapping
    public LibraryStats getStats() {
        long totalBooks = bookService.findAll().size();
        long availableBooks = bookService.getAvailableBooks().size();
        long borrowedBooks = bookService.getBorrowedBooks().size();
        long totalUsers = userService.findAll().size();
        long currentBorrows = borrowHistoryService.getCurrentBorrows().size();

        return new LibraryStats(totalBooks, availableBooks, borrowedBooks, totalUsers, currentBorrows);
    }
}
