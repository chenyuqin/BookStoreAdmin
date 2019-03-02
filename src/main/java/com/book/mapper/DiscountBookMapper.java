package com.book.mapper;

import com.book.entity.DiscountBook;
import com.book.entity.DiscountBookWithBLOBs;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountBookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DiscountBookWithBLOBs record);

    int insertSelective(DiscountBookWithBLOBs record);

    DiscountBookWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DiscountBookWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(DiscountBookWithBLOBs record);

    int updateByPrimaryKey(DiscountBook record);
}