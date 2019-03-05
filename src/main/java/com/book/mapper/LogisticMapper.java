package com.book.mapper;

import com.book.entity.Logistic;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticMapper {
    int deleteByPrimaryKey(String id);

    int insert(Logistic record);

    int insertSelective(Logistic record);

    Logistic selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Logistic record);

    int updateByPrimaryKey(Logistic record);
}