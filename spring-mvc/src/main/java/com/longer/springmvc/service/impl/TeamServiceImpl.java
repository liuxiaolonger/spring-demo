package com.longer.springmvc.service.impl;


import com.longer.springmvc.mapper.TeamMapper;
import com.longer.springmvc.model.Team;
import com.longer.springmvc.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamMapper mapper;

    @Override
    public int deleteByPrimaryKey(Integer classNo) {
        return mapper.deleteByPrimaryKey(classNo);
    }

    @Override
    public int insert(Team record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(Team record) {
        return mapper.insertSelective(record);
    }

    @Override
    public Team selectByPrimaryKey(Integer classNo) {
        return mapper.selectByPrimaryKey(classNo);
    }

    @Override
    public int updateByPrimaryKeySelective(Team record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Team record) {
        return mapper.updateByPrimaryKey(record);
    }
}