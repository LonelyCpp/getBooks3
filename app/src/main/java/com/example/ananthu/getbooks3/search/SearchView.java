package com.example.ananthu.getbooks3.search;

import com.example.ananthu.getbooks3.model.Book;
import com.example.ananthu.getbooks3.util.Toastable;

interface SearchView extends Toastable {

    /**
     * Adds a book relevant to the search query
     * @param book book object parsed from the response XML
     */
    void showBookResult(Book book);

}
