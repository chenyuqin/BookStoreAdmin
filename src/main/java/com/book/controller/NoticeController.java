package com.book.controller;

import com.book.entity.Notice;
import com.book.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("notice/")
public class NoticeController {

    @Autowired
    NoticeMapper noticeMapper;

    @RequestMapping(value = "issueNotice", method = RequestMethod.POST)
    @ResponseBody
    public void issueNotice(@RequestParam("title") String title, @RequestParam("content") String content) {
        //文件目录
//        Path rootLocation = Paths.get("src/main/resources/templates/issue");
//        try {
//            if (Files.notExists(rootLocation)) {
//                Files.createDirectories(rootLocation);
//            }
//            //html文件
//            String name = System.currentTimeMillis() + ".html";
//            Path path = rootLocation.resolve(name);
//            byte[] strToBytes = content.getBytes();
//            Files.write(path, strToBytes);
//
//            Notice notice = new Notice();
//            notice.setTitle(title);
//            notice.setUrl("http://localhost:8090/issue/" + name);
//            noticeMapper.insertSelective(notice);

//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Notice notice = new Notice();
        notice.setTitle(title);
        notice.setUrl(content);
        noticeMapper.insertSelective(notice);
    }
}
