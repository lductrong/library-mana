package com.trong.library.controller;

import com.trong.library.entity.BorrowHistory;
import com.trong.library.service.BorrowHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class BorrowHistoryController {

    @Autowired
    private BorrowHistoryService borrowHistoryService;

    @GetMapping("/user/{userId}")
    public List<BorrowHistory> getUserHistory(@PathVariable Long userId) {
        return borrowHistoryService.getUserHistory(userId);
    }

    @GetMapping("/book/{bookId}")
    public List<BorrowHistory> getBookHistory(@PathVariable Long bookId) {
        return borrowHistoryService.getBookHistory(bookId);
    }

    @GetMapping("/current")
    public List<BorrowHistory> getCurrentBorrows() {
        return borrowHistoryService.getCurrentBorrows();
    }

    @GetMapping
    public List<BorrowHistory> getAllHistory() {
        return borrowHistoryService.getAllHistory();
    }
}
