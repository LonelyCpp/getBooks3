package com.example.ananthu.getbooks3.search;

import com.example.ananthu.getbooks3.model.Book;
import com.example.ananthu.getbooks3.util.Toastable;

public interface SearchView extends Toastable {

    void showBookResult(Book book);

}
