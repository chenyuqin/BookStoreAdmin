package com.book.mapper;

import com.book.entity.Biggestcate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiggestcateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Biggestcate record);

    int insertSelective(Biggestcate record);

    Biggestcate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Biggestcate record);

    int updateByPrimaryKey(Biggestcate record);

    List<Biggestcate> getAllByCategoryId(Integer category_id);
}