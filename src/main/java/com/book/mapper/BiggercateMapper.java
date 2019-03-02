package com.book.mapper;

import com.book.entity.Biggercate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiggercateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Biggercate record);

    int insertSelective(Biggercate record);

    Biggercate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Biggercate record);

    int updateByPrimaryKey(Biggercate record);

    List<Biggercate> getAllByBiggestCateId(Integer biggestCate_id);
}