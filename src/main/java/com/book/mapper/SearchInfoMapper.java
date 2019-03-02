package com.book.mapper;

import com.book.entity.SearchInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SearchInfo record);

    int insertSelective(SearchInfo record);

    SearchInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SearchInfo record);

    int updateByPrimaryKeyWithBLOBs(SearchInfo record);
}