package com.trong.library.service;

import com.trong.library.entity.Book;
import com.trong.library.entity.BorrowHistory;
import com.trong.library.entity.User;
import com.trong.library.repository.BookRepository;
import com.trong.library.repository.BorrowHistoryRepository;
import com.trong.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BorrowHistoryRepository borrowHistoryRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public Book borrowBook(Long bookId, Long userId) {
        Book book = findById(bookId);
        User user = userRepository.findById(userId).orElse(null);

        if (book != null && !book.isBorrowed() && user != null) {
            book.setBorrowedBy(user);
            book.setBorrowed(true);
            
            BorrowHistory history = new BorrowHistory();
            history.setBook(book);
            history.setUser(user);
            history.setBorrowDate(LocalDateTime.now());
            history.setReturned(false);
            borrowHistoryRepository.save(history);
            
            return save(book);
        }
        return null;
    }

    public Book returnBook(Long bookId) {
        Book book = findById(bookId);
        if (book != null && book.isBorrowed()) {
            List<BorrowHistory> histories = borrowHistoryRepository.findByBookId(bookId);
            for (BorrowHistory history : histories) {
                if (!history.isReturned()) {
                    history.setReturnDate(LocalDateTime.now());
                    history.setReturned(true);
                    borrowHistoryRepository.save(history);
                    break;
                }
            }
            
            book.setBorrowedBy(null);
            book.setBorrowed(false);
            return save(book);
        }
        return null;
    }
    
    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
    }
    
    public List<Book> getAvailableBooks() {
        return bookRepository.findByBorrowed(false);
    }
    
    public List<Book> getBorrowedBooks() {
        return bookRepository.findByBorrowed(true);
    }
}
