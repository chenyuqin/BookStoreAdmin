package com.book.controller;

import com.book.DTO.AdminOrderResponseDto;
import com.book.DTO.EveryOrderDto;
import com.book.DTO.OrderBookDto;
import com.book.entity.Address;
import com.book.entity.Logistic;
import com.book.mapper.AddressMapper;
import com.book.mapper.LogisticMapper;
import com.book.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    LogisticMapper logisticMapper;

    @RequestMapping(value = "getAllOrders", method = RequestMethod.POST)
    @ResponseBody
    public Object getAllOrders(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, @RequestParam("status") Integer status) {
        if (status == 0) {
            status = null;
        }
        List<EveryOrderDto> orderByUserId = orderMapper.getOrderByUserId(status, (page-1)*limit, limit);
        for (EveryOrderDto everyOrderDto : orderByUserId) {
            String order_id = everyOrderDto.getOrder_id();
            Integer address_id = everyOrderDto.getAddress_id();
            Address address = addressMapper.selectByPrimaryKey(address_id);
            everyOrderDto.setOrderBookDtos(null);
            everyOrderDto.setAddress(address);
        }

        AdminOrderResponseDto adminOrderResponseDto = new AdminOrderResponseDto();
        adminOrderResponseDto.setCode(0);
        adminOrderResponseDto.setMsg("");
        adminOrderResponseDto.setData(orderByUserId);
        adminOrderResponseDto.setCount(orderMapper.getOrderCountByStatus(status));
        return adminOrderResponseDto;
    }

    @RequestMapping(value = "insertLogistic", method = RequestMethod.POST)
    @ResponseBody
    public void insertLogistic(Logistic logistic) {
        logisticMapper.insertSelective(logistic);
        orderMapper.updateStatusTo3(logistic.getOrder_id());
    }

    @RequestMapping(value = "getOrderBookByOrderId", method = RequestMethod.POST)
    @ResponseBody
        public Object getOrderBookByOrderId(@RequestParam("order_id") String order_id) {
        List<OrderBookDto> orderBookByOrderId = orderMapper.getOrderBookByOrderId(order_id);
        for (OrderBookDto orderBookDto : orderBookByOrderId) {
            String image1 = orderBookDto.getImage1().replace("_x_", "_w_");
            orderBookDto.setImage1(image1);
        }
        return orderBookByOrderId;
    }
}
