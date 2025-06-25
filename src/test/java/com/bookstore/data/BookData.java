package com.bookstore.data;

import com.bookstore.payloads.BookPayload;

import java.util.Random;

public class BookData {

    public static BookPayload getRandomBook() {
        return new BookPayload(
                "Book " + generateRandomSuffix(),
                "Author " + generateRandomSuffix(),
                2024,
                "Test book for API automation"
        );
    }

    private static String generateRandomSuffix() {
        return String.valueOf(new Random().nextInt(10000));
    }
}
