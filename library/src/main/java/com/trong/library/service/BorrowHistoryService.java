package com.trong.library.service;

import com.trong.library.entity.BorrowHistory;
import com.trong.library.repository.BorrowHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowHistoryService {

    @Autowired
    private BorrowHistoryRepository borrowHistoryRepository;

    public List<BorrowHistory> getUserHistory(Long userId) {
        return borrowHistoryRepository.findByUserId(userId);
    }

    public List<BorrowHistory> getBookHistory(Long bookId) {
        return borrowHistoryRepository.findByBookId(bookId);
    }

    public List<BorrowHistory> getCurrentBorrows() {
        return borrowHistoryRepository.findByReturnedFalse();
    }

    public List<BorrowHistory> getAllHistory() {
        return borrowHistoryRepository.findAll();
    }
}
