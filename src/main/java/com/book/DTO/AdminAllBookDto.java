package com.book.DTO;

import com.book.entity.Book;
import lombok.Data;

import java.util.List;

@Data
public class AdminAllBookDto {

    private Integer code;

    private String msg;

    private Integer count;

    private List<Book> data;
}
