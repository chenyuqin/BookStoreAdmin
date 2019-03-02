package com.book.mapper;

import com.book.entity.Book;
import com.book.entity.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminBookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Book record);

    int insertSelective(Book record);

    Book selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Book record);

    int updateByPrimaryKeyWithBLOBs(Book record);

    int updateByPrimaryKey(Book record);
}