package com.book.mapper;

import com.book.DTO.CartDto;
import com.book.entity.Cart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartMapper {
    void deleteByBookId(@Param("book_id") Integer book_id);
}