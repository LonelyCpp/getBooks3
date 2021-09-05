package com.example.getbooks3.api

import com.example.getbooks3.data.BookInfo

class OpenLibraryApi : BooksApi {
    override fun getBook(id: String): BookInfo {
        return BookInfo(
            id = "test_book",
            title = "Hamlet",
            coverId = 8281954,
            firstPublishedYear = 2020,
        )
    }

    override fun search(query: String): Array<BookInfo> {
        return arrayOf(
            BookInfo(
                id = "test_book",
                title = "Hamlet",
                coverId = 8281954,
                firstPublishedYear = 2020,
            )
        )
    }

    override fun list(topic: String): Array<BookInfo> {
        return arrayOf(
            BookInfo(
                id = "test_book",
                title = "Hamlet",
                coverId = 8281954,
                firstPublishedYear = 2020,
            )
        )
    }
}