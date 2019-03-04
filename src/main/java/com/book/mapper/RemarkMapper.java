package com.book.mapper;

import com.book.DTO.EveryRemarkDto;
import com.book.entity.Remark;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RemarkMapper {
    void deleteByBookId(@Param("book_id") Integer book_id);
}