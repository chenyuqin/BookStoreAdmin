package com.book.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SearchDto implements Serializable {
    List<String> cates;

    List<String> publishers;

    List<String> authors;

    List<SearchByCateBookDto> books;

    Integer count;

    Integer pages;
}
