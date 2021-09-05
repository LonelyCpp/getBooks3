package com.example.getbooks3.api

import com.example.getbooks3.data.BookInfo

interface BooksApi {
    /**
     * get info about a book by some identifier
     */
    fun getBook(id: String): BookInfo

    /**
     * get a list of books that match a search query
     */
    fun search(query: String): Array<BookInfo>

    /**
     * get a list of books that are relevant to a topic
     */
    fun list(topic: String): Array<BookInfo>
}