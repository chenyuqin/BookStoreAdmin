package com.book.DTO;

import com.book.entity.Biggercate;
import com.book.entity.Biggestcate;
import com.book.entity.Book;
import lombok.Data;

import java.util.List;

@Data
public class AdminEditBookDto {

    private Book book;

    private List<Biggestcate> biggestcates;

    private List<Biggercate> biggercates;
}
