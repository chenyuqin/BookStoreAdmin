package com.book.DTO;

import lombok.Data;

import java.util.List;

@Data
public class AdminOrderResponseDto {
    private Integer code;

    private String msg;

    private Integer count;

    List<EveryOrderDto> data;
}
