package com.book.service.impl;

import com.book.entity.Biggercate;
import com.book.entity.Biggestcate;
import com.book.entity.Book;
import com.book.mapper.BiggercateMapper;
import com.book.mapper.BiggestcateMapper;
import com.book.mapper.BookMapper;
import com.book.service.AdminAllBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminAllBooksServiceImpl implements AdminAllBooksService {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BiggestcateMapper biggestcateMapper;

    @Autowired
    BiggercateMapper biggercateMapper;

    @Override
    public Integer getCountByCategoryId(Integer category, String biggestCate, String biggerCate) {
        return bookMapper.getCountByCategoryId(category, biggestCate, biggerCate);
    }

    @Override
    public List<Biggestcate> getAllByCategoryId(Integer category_id) {
        return biggestcateMapper.getAllByCategoryId(category_id);
    }

    @Override
    public List<Book> getAllBooks(Integer page, Integer limit, Integer category, String biggestCate, String biggerCate) {
        return bookMapper.getAllBooks(page, limit, category, biggestCate, biggerCate);
    }

    @Override
    public List<Biggercate> getAllByBiggestCate(Integer biggestCate) {
        return biggercateMapper.getAllByBiggestCateId(biggestCate);
    }

}
