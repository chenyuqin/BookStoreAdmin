package com.book.controller;

import com.book.DTO.AdminAllBookDto;
import com.book.DTO.AdminEditBookDto;
import com.book.VO.SearchVO;
import com.book.entity.Biggercate;
import com.book.entity.Biggestcate;
import com.book.entity.Book;
import com.book.entity.DiscountBookWithBLOBs;
import com.book.mapper.*;
import com.book.service.AdminAllBooksService;
import com.book.service.BookDetailService;
import com.book.service.SearchService;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("book/")
public class BookController {

    @Autowired
    AdminAllBooksService adminAllBooksService;

    @Autowired
    SearchService searchService;

    @Autowired
    BookDetailService bookDetailService;

    @Autowired
    BiggestcateMapper biggestcateMapper;

    @Autowired
    BiggercateMapper biggercateMapper;

    @Autowired
    AdminBookMapper adminBookMapper;

    @Autowired
    DiscountBookMapper discountBookMapper;

    @Autowired
    SearchInfoMapper searchInfoMapper;

    @RequestMapping(value = "getAllBooks", method = RequestMethod.POST)
    @ResponseBody

    public Object getAllBooks(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, @RequestParam("category") Integer category, @RequestParam("biggestCate") String biggestCate, @RequestParam("biggerCate") String biggerCate) {
        if (biggestCate.equals("0")) {
            biggestCate = null;
        }
        if (biggerCate.equals("0")) {
            biggerCate = null;
        }
        AdminAllBookDto adminAllBookDto = new AdminAllBookDto();
        adminAllBookDto.setCode(0);
        adminAllBookDto.setCount(adminAllBooksService.getCountByCategoryId(category, biggestCate, biggerCate));
        List<Book> allBooks = adminAllBooksService.getAllBooks((page - 1) * limit, limit, category, biggestCate, biggerCate);
        for (Book book : allBooks) {
            String image1 = book.getImage1().replace("_x_", "_w_");
            book.setImage1(image1);
        }
        adminAllBookDto.setData(allBooks);
        return adminAllBookDto;
    }

    @RequestMapping(value = "getAllByCategoryId", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllByCategoryId(@RequestParam("category_id") Integer category_id) {
        List<Biggestcate> allByCategoryId = adminAllBooksService.getAllByCategoryId(category_id);
        return allByCategoryId;
    }

    @RequestMapping(value = "getAllByBiggestCate", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllByBiggestCate(@RequestParam("biggestCate") Integer biggestCate) {
        List<Biggercate> allByCategoryId = adminAllBooksService.getAllByBiggestCate(biggestCate);
        return allByCategoryId;
    }

    @RequestMapping(value = "getAllBooksBySearch", method = RequestMethod.POST)
    @ResponseBody
    public Object getAllBooksBySearch(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, @RequestParam("queryString") String queryString) {
        List<Integer> ids = searchService.getIdsByQueryString(queryString, page, limit);
        SearchVO searchVO = new SearchVO();
        searchVO.setQueryString(queryString);
        AdminAllBookDto adminAllBookDto = new AdminAllBookDto();
        adminAllBookDto.setCode(0);
        adminAllBookDto.setCount(searchService.getCountByQueryString(searchVO));
        List<Book> allBooks = new ArrayList<>();
        for (Integer id : ids) {
            Book book = bookDetailService.getBookById(id);
            String image1 = book.getImage1().replace("_x_", "_w_");
            book.setImage1(image1);
            allBooks.add(book);
        }
        adminAllBookDto.setData(allBooks);
        return adminAllBookDto;
    }

    @RequestMapping(value = "getBookById", method = RequestMethod.POST)
    @ResponseBody
    public Object getBookById(@RequestParam("id") Integer id) {
        AdminEditBookDto adminEditBookDto = new AdminEditBookDto();

        Book book = bookDetailService.getBookById(id);
        List<Biggestcate> allByCategoryId = adminAllBooksService.getAllByCategoryId(book.getCategory());
        for (Biggestcate biggestcate : allByCategoryId) {
            if (biggestcate.getCateName().equals(book.getBiggestCate())) {
                List<Biggercate> allByBiggestCate = adminAllBooksService.getAllByBiggestCate(biggestcate.getId());
                adminEditBookDto.setBiggercates(allByBiggestCate);
                break;
            }
        }
        adminEditBookDto.setBook(book);
        adminEditBookDto.setBiggestcates(allByCategoryId);
        return adminEditBookDto;
    }

    @RequestMapping(value = "editBookById", method = RequestMethod.POST)
    @ResponseBody
    public void editBookById(@RequestParam("id") Integer id, @RequestParam("json") String json) {
        JSONObject topicObject = JSONObject.fromObject(json);
        Book book = (Book) JSONObject.toBean(topicObject, Book.class);
        book.setId(id);
        book.setBiggestCate(biggestcateMapper.selectByPrimaryKey(Integer.parseInt(book.getBiggestCate())).getCateName());
        book.setBiggerCate(biggercateMapper.selectByPrimaryKey(Integer.parseInt(book.getBiggerCate())).getCateName());
        book.setDiscount(String.format("%.2f", Double.parseDouble(book.getPrice()) / Double.parseDouble(book.getPrePrice()) * 10));

        Book oldBook = bookDetailService.getBookById(id);
        if (Double.parseDouble(oldBook.getDiscount()) < Double.parseDouble(book.getDiscount())) {
            discountBookMapper.deleteByPrimaryKey(id);
            adminBookMapper.updateByPrimaryKeySelective(book);
        } else if (Double.parseDouble(oldBook.getDiscount()) > Double.parseDouble(book.getDiscount())) {
            discountBookMapper.deleteByPrimaryKey(id);
            adminBookMapper.updateByPrimaryKeySelective(book);
            book = bookDetailService.getBookById(id);
            DiscountBookWithBLOBs discountBookWithBLOBs = new DiscountBookWithBLOBs();
            BeanUtils.copyProperties(book, discountBookWithBLOBs);
            discountBookWithBLOBs.setStock(oldBook.getStock() + "");
            discountBookWithBLOBs.setRemark(oldBook.getRemark() + "");
            discountBookWithBLOBs.setSales(oldBook.getSales() + "");
            discountBookWithBLOBs.setGroundingTime(null);
            discountBookMapper.insertSelective(discountBookWithBLOBs);
        }

    }

    @RequestMapping(value = "delBookById", method = RequestMethod.POST)
    @ResponseBody
    public void delBookById(@RequestParam("id") Integer id) {
        adminBookMapper.deleteByPrimaryKey(id);
        discountBookMapper.deleteByPrimaryKey(id);
        searchInfoMapper.deleteByPrimaryKey(id);
    }

    @RequestMapping(value = "insertBook", method = RequestMethod.POST)
    @ResponseBody
    public void insertBook(@RequestParam("json") String json) {
        JSONObject topicObject = JSONObject.fromObject(json);
        Book book = (Book) JSONObject.toBean(topicObject, Book.class);
        book.setBiggestCate(biggestcateMapper.selectByPrimaryKey(Integer.parseInt(book.getBiggestCate())).getCateName());
        book.setBiggerCate(biggercateMapper.selectByPrimaryKey(Integer.parseInt(book.getBiggerCate())).getCateName());
        book.setStar("0.0%");
        book.setDiscount(String.format("%.2f", Double.parseDouble(book.getPrice()) / Double.parseDouble(book.getPrePrice()) * 10));
        adminBookMapper.insertSelective(book);
    }
}
