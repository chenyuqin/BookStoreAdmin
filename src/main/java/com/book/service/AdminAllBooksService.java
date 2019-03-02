package com.book.service;

import com.book.entity.Biggercate;
import com.book.entity.Biggestcate;
import com.book.entity.Book;

import java.util.List;

public interface AdminAllBooksService {

    Integer getCountByCategoryId(Integer category, String biggestCate, String biggerCate);

    List<Book> getAllBooks(Integer page, Integer limit, Integer category, String biggestCate, String biggerCate);

    List<Biggestcate> getAllByCategoryId(Integer category_id);

    List<Biggercate> getAllByBiggestCate(Integer biggestCate);
}
