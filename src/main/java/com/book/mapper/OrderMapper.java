package com.book.mapper;

import com.book.DTO.EveryOrderDto;
import com.book.DTO.OrderBookDto;
import com.book.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByOrderIdSelective(Order record);

    int updateByPrimaryKey(Order record);

    List<EveryOrderDto> getOrderByUserId(@Param("status") Integer status, @Param("page") Integer page, @Param("limit") Integer limit);

    List<OrderBookDto> getOrderBookByOrderId(@Param("order_id") String order_id);

    List<Integer> getNotApprBooksByUserID(Integer user_id);

    Integer getOrderCountByStatus(@Param("status") Integer status);

    void updateStatusTo3(@Param("order_id") String order_id);

}