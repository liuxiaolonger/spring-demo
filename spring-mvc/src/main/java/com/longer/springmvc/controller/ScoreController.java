package com.longer.springmvc.controller;

import com.longer.springmvc.model.Score;
import com.longer.springmvc.service.ScoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ScoreController {
    public static final Logger logger = LoggerFactory.getLogger(ScoreController.class);
    @Autowired
    private ScoreService service;
    
    @GetMapping("/score/findmax/")
    public ModelAndView findMax() {
        List<Score> max = service.findMax();
        ModelAndView mv = new ModelAndView();
        logger.info("查询结果",max);
        mv.addObject("list", max);
        mv.setViewName("score.html");
        return  mv;
    }
}
