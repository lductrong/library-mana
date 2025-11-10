package com.trong.library.repository;

import com.trong.library.entity.BorrowHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BorrowHistoryRepository extends JpaRepository<BorrowHistory, Long> {
    List<BorrowHistory> findByUserId(Long userId);
    List<BorrowHistory> findByBookId(Long bookId);
    List<BorrowHistory> findByReturnedFalse();
}
