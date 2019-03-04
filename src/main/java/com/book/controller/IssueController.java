package com.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("issue")
public class IssueController {

    @GetMapping("/{page}")
    public String getPage(@PathVariable("page")String page) {
        return "issue/" + page;
    }
}
