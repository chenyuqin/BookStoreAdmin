package com.book.controller;

import com.book.entity.Slideshow;
import com.book.mapper.SlideshowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("slideshow/")
public class SlideShowController {

    @Autowired
    SlideshowMapper slideshowMapper;

    @RequestMapping(value = "getSlideshows", method = RequestMethod.GET)
    @ResponseBody
    public Object getSlideshows() {

        return slideshowMapper.selectNewFive();
    }

    @RequestMapping(value = "getSlideshowById", method = RequestMethod.GET)
    @ResponseBody
    public Object getSlideshowById(@RequestParam("id") Integer id) {

        return slideshowMapper.selectByPrimaryKey(id);
    }

    @RequestMapping(value = "updateSlideshowById", method = RequestMethod.POST)
    @ResponseBody
    public void updateSlideshowById(Slideshow slideshow) {

        slideshowMapper.updateByPrimaryKeySelective(slideshow);
    }
}
