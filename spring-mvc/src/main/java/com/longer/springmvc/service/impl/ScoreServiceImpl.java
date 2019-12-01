package com.longer.springmvc.service.impl;


import com.longer.springmvc.mapper.ScoreMapper;
import com.longer.springmvc.model.Score;
import com.longer.springmvc.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    private ScoreMapper mapper;
    @Override
    public int insert(Score record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(Score record) {
        return mapper.insertSelective(record);
    }

    @Override
    public List<Score> findMax() {
        return mapper.findMax();
    }
}