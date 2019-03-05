package com.book.controller;

import com.book.entity.Admin;
import com.book.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    AdminMapper adminMapper;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestParam("name") String name, @RequestParam("password") String password, HttpServletRequest request) {
        Admin admin = new Admin();
        admin.setName(name);
        admin.setPassword(password);
        admin = adminMapper.getByNameAndPassword(admin);
        if (admin == null) {
            return 1; //登录失败
        } else {
            request.getSession().setAttribute("userName", admin.getName());
            return 0; //登录成功
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
