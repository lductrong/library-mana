package com.trong.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LibraryStats {
    private long totalBooks;
    private long availableBooks;
    private long borrowedBooks;
    private long totalUsers;
    private long currentBorrows;
}
